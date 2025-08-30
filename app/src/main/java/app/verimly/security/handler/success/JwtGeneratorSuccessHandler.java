package app.verimly.security.handler.success;

import app.verimly.commons.core.security.SecurityUser;
import app.verimly.security.cookie.CookieHelper;
import app.verimly.security.jwt.JwtHelper;
import app.verimly.user.adapter.web.dto.response.UserDetailsWebResponse;
import app.verimly.user.adapter.web.mapper.UserWebMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtGeneratorSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtHelper jwtHelper;
    private final CookieHelper cookieHelper;
    private final ObjectMapper objectMapper;
    private final UserWebMapper userWebMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("Handling authentication succeed.");

        assert authentication.getPrincipal() instanceof SecurityUser : "Principal must be instance of SecurityUser";

        SecurityUser principal = (SecurityUser) authentication.getPrincipal();

        String accessToken = jwtHelper.generateJwtToken(principal.getId().toString(), principal.getUsername());


        ResponseCookie cookie = cookieHelper.createAccessTokenCookie(accessToken);

        writeUserDetailsToResponseBody(response , principal);
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        log.debug("Authentication success handled by generation a JWT token and put it in cookie.");
    }

    private void writeUserDetailsToResponseBody(HttpServletResponse response, SecurityUser user) {
        try {
            UserDetailsWebResponse details = userWebMapper.toUserDetailsWebResponse(user);
            objectMapper.writeValue(response.getWriter(), details);
        } catch (Exception e) {
            throw new RuntimeException(("Failed to write user details to successful login response. " +
                    "User: [ID: %s, Email: %s]").formatted(user.getId(), user.getEmail()));
        }
    }
}

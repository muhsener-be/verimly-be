package app.verimly.security.handler.success;

import app.verimly.security.cookie.CookieHelper;
import app.verimly.security.jwt.JwtHelper;
import app.verimly.commons.core.security.SecurityUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("Handling authentication succeed.");

        assert authentication.getPrincipal() instanceof SecurityUser : "Principal must be instance of SecurityUser";

        SecurityUser principal = (SecurityUser) authentication.getPrincipal();

        String accessToken = jwtHelper.generateJwtToken(principal.getId().toString(), principal.getUsername());

        Cookie cookie = cookieHelper.createAccessTokenCookie(accessToken);

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
        log.debug("Authentication success handled by generation a JWT token and put it in cookie.");
    }
}

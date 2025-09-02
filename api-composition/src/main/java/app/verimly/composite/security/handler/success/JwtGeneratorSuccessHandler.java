package app.verimly.composite.security.handler.success;

import app.verimly.commons.core.security.SecurityUser;
import app.verimly.composite.adapter.web.dto.response.UserWithSessionsWebResponse;
import app.verimly.composite.security.cookie.CookieHelper;
import app.verimly.composite.security.jwt.JwtHelper;
import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import app.verimly.task.adapter.web.mapper.SessionWebMapper;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.ports.in.SessionApplicationService;
import app.verimly.user.adapter.web.dto.response.UserDetailsWebResponse;
import app.verimly.user.adapter.web.mapper.UserWebMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtGeneratorSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtHelper jwtHelper;
    private final CookieHelper cookieHelper;
    private final ObjectMapper objectMapper;
    private final UserWebMapper userWebMapper;
    private final SessionApplicationService sessionApplicationService;
    private final SessionWebMapper sessionWebMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("Handling authentication succeed.");

        assert authentication.getPrincipal() instanceof SecurityUser : "Principal must be instance of SecurityUser";

        SecurityUser principal = (SecurityUser) authentication.getPrincipal();

        String accessToken = jwtHelper.generateJwtToken(principal.getId().toString(), principal.getUsername());


        ResponseCookie cookie = cookieHelper.createAccessTokenCookie(accessToken);

        addCookieToResponse(response, cookie);
        UserWithSessionsWebResponse responseBody = prepareResponseBody(principal);
        writeResponseBody(response, responseBody);
        log.debug("User logged in successfully: [ID: {}, Email: {}]", principal.getId(), principal.getEmail());
    }

    private static void addCookieToResponse(HttpServletResponse response, ResponseCookie cookie) {
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void writeResponseBody(HttpServletResponse response, UserWithSessionsWebResponse responseBody) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getWriter(), responseBody);
            response.flushBuffer();
        } catch (Exception e) {
            UserDetailsWebResponse user = responseBody.getUser();
            throw new RuntimeException("Failed to write response body after successful login. User: [ID: %s, Email: %s]".formatted(user.getId(), user.getEmail()));
        }
    }


    private UserWithSessionsWebResponse prepareResponseBody(SecurityUser user) {

        UserDetailsWebResponse userDetails = userWebMapper.toUserDetailsWebResponse(user);
        List<SessionSummaryWebResponse> sessions = fetchUserActiveSessions();
        return new UserWithSessionsWebResponse(userDetails, sessions);

    }

    private List<SessionSummaryWebResponse> fetchUserActiveSessions() {
        try {
            List<SessionSummaryData> sessions = sessionApplicationService.fetchActiveSessions();
            return sessionWebMapper.toWebResponses(sessions);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user active sessions: " + e.getMessage(), e);
        }
    }
}

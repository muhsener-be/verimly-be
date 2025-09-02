package app.verimly.composite.security.handler;

import app.verimly.commons.core.security.SecurityUser;
import app.verimly.composite.security.cookie.CookieHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Handles successful logout events in the application.
 * <p>
 * This handler is responsible for clearing authentication cookies and logging user logout activity.
 * It is used by Spring Security to process logout success events.
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * Helper for cookie operations, used to create logout cookies.
     */
    private final CookieHelper cookieHelper;

    /**
     * Called when a user has been successfully logged out.
     * Sets the logout cookie and logs the event.
     *
     * @param request        the HttpServletRequest
     * @param response       the HttpServletResponse
     * @param authentication the current authentication (may be null)
     * @throws IOException      if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("Handling logout success...");

        ResponseCookie logoutCookie = cookieHelper.createLogoutCookie();


        response.setHeader(HttpHeaders.SET_COOKIE, logoutCookie.toString());
        if (authentication != null) {
            SecurityUser principal = (SecurityUser) authentication.getPrincipal();
            log.info("User logout successfully. [ID: {}, Email: {}]", principal.getId(), principal.getEmail());
        }
    }
}

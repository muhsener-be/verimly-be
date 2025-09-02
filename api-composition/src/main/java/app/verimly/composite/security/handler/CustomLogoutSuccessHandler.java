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

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final CookieHelper cookieHelper;


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

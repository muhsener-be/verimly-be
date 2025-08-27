package app.verimly.security.filter;

import app.verimly.commons.core.security.SecurityUser;
import app.verimly.config.AccessTokenCookieProperties;
import app.verimly.config.SecurityProperties;
import app.verimly.security.jwt.JwtException;
import app.verimly.security.jwt.JwtHelper;
import app.verimly.security.jwt.VerifiedToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AccessTokenCookieProperties properties;
    private final JwtHelper jwtHelper;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!requiresFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("Access token cookie is detected.");

        Cookie cookie = extractCookie(request);
        String token = cookie.getValue();

        try {
            VerifiedToken verifiedToken = jwtHelper.verifyToken(token);
            SecurityUser securityUser = new SecurityUser(verifiedToken.subject(), verifiedToken.email(), null);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(securityUser, "", new ArrayList<>());

            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            emptyContext.setAuthentication(authToken);
            SecurityContextHolder.setContext(emptyContext);
            log.trace("SecurityContextHolder is set to User with [ID: {}, Email: {}]", securityUser.getId(), securityUser.getUsername());
            filterChain.doFilter(request, response);
        } catch (JwtException exception) {
            authenticationEntryPoint.commence(request, response, exception);
            return;
        } finally {
            SecurityContextHolder.clearContext();
        }


    }


    private Cookie extractCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(this::cookieNameMatches)
                .toList()
                .getFirst();
    }

    private boolean requiresFilter(HttpServletRequest request) {
        if (isLoginEndpoint(request)) {
            return false;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return false;
        return Arrays.stream(cookies).anyMatch(this::cookieNameMatches);
    }

    private boolean isLoginEndpoint(HttpServletRequest request) {
        return request.getRequestURI().equals(securityProperties.getLoginPath());

    }


    private boolean cookieNameMatches(Cookie cookie) {
        return cookie.getName().equals(properties.getName());
    }
}

package app.verimly.composite.security.filter;

import app.verimly.commons.core.security.SecurityUser;
import app.verimly.composite.security.config.AccessTokenCookieProperties;
import app.verimly.composite.security.jwt.JwtException;
import app.verimly.composite.security.jwt.JwtHelper;
import app.verimly.composite.security.jwt.VerifiedToken;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AccessTokenCookieProperties properties;
    private final JwtHelper jwtHelper;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final List<String> permitAllPaths;
    private final AntPathMatcher matcher = new AntPathMatcher();

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

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestPath = request.getServletPath();
        return permitAllPaths.stream().anyMatch(pattern -> matcher.match(pattern, requestPath));

    }

    private boolean requiresFilter(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return false;
        return Arrays.stream(cookies).anyMatch(this::cookieNameMatches);
    }


    private boolean cookieNameMatches(Cookie cookie) {
        return cookie.getName().equals(properties.getName());
    }
}

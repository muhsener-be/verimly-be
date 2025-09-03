package app.verimly.composite.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CorrelationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String requestId = Optional.ofNullable(req.getHeader("X-Request-ID"))
                .filter(h -> !h.isBlank())
                .orElse(UUID.randomUUID().toString());

        try {
            MDC.put("request_id", requestId);
            MDC.put("ip_address", req.getRemoteAddr());
            MDC.put("user_agent", Optional.ofNullable(req.getHeader("User-Agent")).orElse("unknown"));
            MDC.put("source", "web-app"); // ihtiyacına göre sabitle/çıkart

            chain.doFilter(req, res);
        } finally {
            MDC.clear();
        }
    }
}

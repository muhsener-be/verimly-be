package app.verimly.composite.security.handler.auth_entrypoint;

import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.commons.core.web.response.ErrorResponseFactory;
import app.verimly.commons.core.web.response.UnauthenticatedErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final ErrorResponseFactory errorResponseFactory;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.debug("Handling authentication entry point. [Reason: {}]", authException.getMessage());

        UnauthenticatedErrorResponse error = errorResponseFactory.unauthenticated()
                .message("Full authentication is required to access this resource")
                .path(request.getRequestURI()).build();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), error);
        response.flushBuffer();
    }
}

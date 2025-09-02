package app.verimly.composite.security.handler.access_denied;

import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.web.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.debug("Handling access denied exception. [Reason: {}]", accessDeniedException.getMessage());

        ErrorResponse error = ErrorResponse.forbidden(NoPermissionException.errorMessage.code(), accessDeniedException.getMessage(), request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(response.getWriter(), error);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.flushBuffer();

    }
}

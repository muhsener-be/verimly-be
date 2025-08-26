package app.verimly.commons.core.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, Map<String, Object>> additional;


    public static ErrorResponse badRequest(String message, String path, Map<String, Map<String, Object>> additional) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(400)
                .error("Bad request")
                .message(message)
                .path(path)
                .additional(additional)
                .build();
    }


    public static ErrorResponse conflict(String message, String path) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(409)
                .error("Conflict.")
                .message(message)
                .path(path)
                .additional(null)
                .build();
    }

    public static ErrorResponse internalServerError(String message, String path) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(500)
                .error("Internal server error.")
                .message(message)
                .path(path)
                .additional(null)
                .build();
    }

    public static ErrorResponse unauthorized(String message, String path) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(401)
                .error("Unauthorized")
                .message(message)
                .path(path)
                .additional(null)
                .build();
    }

    public static ErrorResponse forbidden(String message, String path) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(403)
                .error("Forbidden")
                .message(message)
                .path(path)
                .additional(null)
                .build();
    }
}

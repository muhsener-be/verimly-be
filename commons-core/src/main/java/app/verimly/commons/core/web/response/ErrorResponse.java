package app.verimly.commons.core.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "TestErrorResponse", description = "Standard error response structure for API endpoints.")
public class ErrorResponse {
    @Schema(description = "Timestamp when the error occurred.", example = "2025-08-31T12:34:56.789Z")
    private Instant timestamp;
    @Schema(description = "HTTP status code of the error.", example = "400")
    private int status;
    @Schema(description = "Error code or type.", example = "task.owner-not-exist")
    private String error;

    @Schema(description = "Detailed error message.", example = "Invalid input provided.")
    private String message;
    @Schema(description = "Request path where the error occurred.", example = "/api/v1/resource")
    private String path;
    @Schema(description = "Additional error details, if any.",
            example = """
                    {
                        "createUserWebRequest": {
                            "firstName": "Name part must be between 1 and 50 characters and cannot be blank."
                        }
                    }
                    """)
    private Map<String, Map<String, Object>> additional;


    public static ErrorResponse badRequest(String errorCode, String message, String path) {
        return badRequest(errorCode, message, path, null);
    }

    public static ErrorResponse badRequest(String errorCode, String message, String path, Map<String, Map<String, Object>> additional) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(400)
                .error(errorCode)
                .message(message)
                .path(path)
                .additional(additional)
                .build();
    }


    public static ErrorResponse conflict(String errorCode, String message, String path) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(409)
                .error(errorCode)
                .message(message)
                .path(path)
                .additional(null)
                .build();
    }

    public static ErrorResponse internalServerError(String message, String path) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(500)
                .error("internal")
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

    public static ErrorResponse forbidden(String errorCode, String message, String path) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(403)
                .error(errorCode)
                .message(message)
                .path(path)
                .additional(null)
                .build();
    }

    public static ErrorResponse notFound(String errorCode, String message, String path) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(404)
                .error(errorCode)
                .message(message)
                .path(path)
                .additional(null)
                .build();
    }
}

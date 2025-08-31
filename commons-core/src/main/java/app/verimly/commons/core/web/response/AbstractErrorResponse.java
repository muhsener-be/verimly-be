package app.verimly.commons.core.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.Instant;


@Getter
@Schema(name = "AbstractErrorResponse")
public abstract class AbstractErrorResponse {

    @Schema(description = "Timestamp when the error occurred.", example = "2025-08-31T12:34:56.789Z")
    private Instant timestamp;

    @Schema(description = "HTTP status code of the error.", example = "400")
    private int status;

    @Schema(description = "Request path where the error occurred.", example = "/api/v1/resource")
    private String path;

    @Schema(description = "Detailed error message.", example = "Invalid input provided.")
    private String message;

    public AbstractErrorResponse() {
    }

    public AbstractErrorResponse(Instant timestamp, int status, String path, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
        this.message = message;
    }
}

package app.verimly.commons.core.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.Instant;


@Getter
@Schema(name = "AbstractErrorResponse")
public abstract class AbstractErrorResponse {

    @Schema(description = "Timestamp when the error occurred.",
            example = "2025-08-31T12:34:56.789Z",
            nullable = false
    )
    private Instant timestamp;

    @Schema(description = "HTTP status code of the error.", example = "400", nullable = false)
    private int status;

    @Schema(description = "Request path where the error occurred.", example = "/api/v1/resource", nullable = false)
    private String path;

    @Schema(description = "Detailed error message.", example = "Invalid input provided.", nullable = true)
    private String message;

    @Schema(description = "A machine-readable error code that identifies the specific error condition. This code can be used for programmatic error handling and internationalization.", nullable = true)
    @JsonProperty("error_code")
    private String errorCode;


    public AbstractErrorResponse() {
    }

    public AbstractErrorResponse(Instant timestamp, int status, String path, String message, String errorCode) {
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
        this.message = message;
        this.errorCode = errorCode;
    }
}

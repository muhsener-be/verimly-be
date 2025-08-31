package app.verimly.commons.core.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;


@NoArgsConstructor
@Getter
@Schema(name = "NotFoundErrorResponse")
public class NotFoundErrorResponse extends AbstractErrorResponse {
    @Schema(description = "Type of the resource that is not found.",example = "TASK")
    @JsonProperty("resource_type")
    private String resourceType;

    @Schema(description = "ID of the resource that is not found.", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    @JsonProperty("resource_id")
    private String resourceId;

    @Builder
    public NotFoundErrorResponse(String path, String message, String resourceType, String resourceId) {
        super(Instant.now(), HttpStatus.NOT_FOUND.value(), path, message,"resource-not-found");
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
}

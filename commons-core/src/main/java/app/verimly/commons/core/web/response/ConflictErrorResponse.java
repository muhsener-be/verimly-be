package app.verimly.commons.core.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Provides structured details about the resource conflict.")
public class ConflictErrorResponse extends AbstractErrorResponse {

    @Schema(description = "The type of resource that caused the conflict.", example = "SESSION")
    private String resourceType;

    @Schema(description = "A unique identifier for the conflicting resource.", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private String resourceId;

    @Schema(description = "A summary of the conflicting resource itself. The structure of this object depends on the resourceType.")
    private Object conflictingResource;


    @Builder
    public ConflictErrorResponse(String path, String message, String errorCode, String resourceType, String resourceId, Object conflictingResource) {
        super(Instant.now(), 409, path, message, errorCode);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.conflictingResource = conflictingResource;
    }
}

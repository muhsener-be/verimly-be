package app.verimly.task.adapter.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Request object for moving task to folder.")
public class MoveTaskToFolderWebRequest {

    @NotNull(message = "folderId.required")
    @JsonProperty("folder_id")
    @Schema(
            description = "folderId to which the task be moved.",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID folderId;
}

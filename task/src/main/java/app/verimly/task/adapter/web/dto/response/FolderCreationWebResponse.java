package app.verimly.task.adapter.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
@Schema(description = "Response object for folder creation containing folder details.")
public class FolderCreationWebResponse {
    @Schema(description = "Unique identifier of the folder", example = "b3b6c8e2-8e6d-4e7a-9c2e-1a2b3c4d5e6f")
    private UUID id;

    @JsonProperty("owner_id")
    @Schema(description = "Unique identifier of the folder owner", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
    private UUID ownerId;

    @Schema(description = "Name of the folder", example = "Project Documents")
    private String name;

    @Schema(description = "Description of the folder", example = "Contains all project-related documents.")
    private String description;

    @JsonProperty("label_color_hex")
    @Schema(description = "Hexadecimal color code for the folder label", example = "#FF5733")
    private String labelColor;
}

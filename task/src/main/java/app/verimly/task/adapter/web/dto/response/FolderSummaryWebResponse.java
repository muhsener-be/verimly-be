package app.verimly.task.adapter.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Summary of a folder")
public class FolderSummaryWebResponse {

    @Schema(description = "Unique identifier of the folder", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    @Schema(description = "Name of the folder", example = "My Work")
    private String name;
    @JsonProperty("label_color_hex")
    @Schema(description = "Label color of the folder in HEX format", example = "#FF0000")
    private String labelColor;

}

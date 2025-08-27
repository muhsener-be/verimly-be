package app.verimly.task.adapter.web.dto.request;

import app.verimly.task.adapter.web.validation.HexFormat;
import app.verimly.task.domain.vo.folder.FolderDescription;
import app.verimly.task.domain.vo.folder.FolderName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Request object for creating a new folder.")
public class CreateFolderWebRequest {
    @NotBlank(message = "folder.name-not-exists")
    @Length(min = 1, max = FolderName.MAX_LENGTH, message = "folder-name.length")
    @Schema(description = "Name of the folder to be created.", example = "Project Documents", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Length(max = FolderDescription.MAX_LENGTH, message = "folder-description.length")
    @Schema(description = "Description of the folder.", example = "Contains all project-related documents.")
    private String description;

    @JsonProperty("label_color_hex")
    @HexFormat(message = "color.hex-format")
    @Schema(description = "Hexadecimal color code for the folder label.", example = "#FF5733")
    private String labelColor;
}

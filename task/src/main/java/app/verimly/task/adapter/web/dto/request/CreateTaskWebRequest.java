package app.verimly.task.adapter.web.dto.request;

import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Schema(description = "Request object for creating a new task")
public class CreateTaskWebRequest {


    @NotNull(message = "task.folder-not-exist")
    @Schema(
            description = "ID of the folder where the task will be created",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @JsonProperty("folder_id")
    private UUID folderId;

    @NotBlank(message = "task.name-not-exist")
    @Size(max = TaskName.MAX_LENGTH, message = "Task name must be at most {max} characters")
    @Schema(
            description = "Name of the task",
            example = "Complete project documentation",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = TaskName.MAX_LENGTH
    )
    private String name;

    @Schema(
            description = "Detailed description of the task",
            example = "Update all API documentation with the latest endpoint changes",
            maxLength = TaskDescription.MAX_LENGTH,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = TaskDescription.MAX_LENGTH, message = "Description must be at most {max} characters")
    private String description;

    @Schema(
            description = "Due date and time for the task in ISO-8601 format. It must be in the future.",
            example = "2025-12-31T23:59:59+03:00",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @JsonProperty("due_date")
    @Future(message = "due-date.past")
    private ZonedDateTime dueDate;

    @Schema(
            description = "Priority level of the task",
            example = "HIGH",
            allowableValues = {"LOW", "MEDIUM", "HIGH"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String priority;
}

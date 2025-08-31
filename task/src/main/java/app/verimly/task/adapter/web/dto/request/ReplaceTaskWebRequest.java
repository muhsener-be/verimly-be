package app.verimly.task.adapter.web.dto.request;

import app.verimly.task.domain.vo.task.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "Request object for replacing an existing task")
public class ReplaceTaskWebRequest {

    @Schema(description = "Name of the task", example = "Complete project documentation")
    @NotBlank(message = "folder-name.not-null")
    private String name;

    @Schema(description = "Detailed description of the task",
            example = "Update all API documentation with the latest endpoints",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @JsonProperty("due_date")
    @Schema(description = "Due date and time for the task in ISO-8601 format. It cannot be in the past.",
            example = "2025-12-31T23:59:59+03:00",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Future(message = "due-data.past")
    private ZonedDateTime dueDate;

    @Schema(description = "Current status of the task",
            example = "IN_PROGRESS",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "task-status.not-null")
    private TaskStatus status;

    @Schema(description = "Priority level of the task", example = "HIGH")
    private String priority;

}

package app.verimly.task.adapter.web.dto.response;

import app.verimly.task.domain.vo.task.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Summary of a task. ZonedDateTime fields are adjusted according to the user's timezone, obtained from the 'X-TimeZone' header.")
public class TaskSummaryWebResponse {
    @Schema(description = "Unique identifier of the task.", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID id;

    @JsonProperty("owner_id")
    @Schema(description = "Unique identifier of the task owner.", example = "a123f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID ownerId;

    @JsonProperty("folder_id")
    @Schema(description = "Unique identifier of the folder containing the task.", example = "b456f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID folderId;

    @Schema(description = "Name of the task.", example = "Finish documentation")
    private String name;

    @Schema(description = "Description of the task.", example = "Complete the API documentation for the release.")
    private String description;

    @JsonProperty("due_date")
    @Schema(description = "Due date of the task, adjusted to user's timezone from 'X-TimeZone' header.", example = "2025-08-31T17:00:00+03:00")
    private ZonedDateTime dueDate;

    @Schema(description = "Current status of the task.", example = "IN_PROGRESS")
    private TaskStatus status;

    @Schema(description = "Priority of the task.", example = "HIGH")
    private String priority;

    @JsonProperty("created_at")
    @Schema(description = "Creation timestamp, adjusted to user's timezone from 'X-TimeZone' header.", example = "2025-08-30T09:00:00+03:00")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    @Schema(description = "Last update timestamp, adjusted to user's timezone from 'X-TimeZone' header.", example = "2025-08-31T12:00:00+03:00")
    private ZonedDateTime updatedAt;
}

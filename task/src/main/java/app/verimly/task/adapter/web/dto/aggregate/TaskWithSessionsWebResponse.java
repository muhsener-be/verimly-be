package app.verimly.task.adapter.web.dto.aggregate;

import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Task details including its associated sessions and time tracking information")
public class TaskWithSessionsWebResponse {

    @Schema(description = "Unique identifier of the task", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID id;

    @JsonProperty("owner_id")
    @Schema(description = "ID of the user who owns the task", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID ownerId;

    @JsonProperty("folder_id")
    @Schema(description = "ID of the folder this task belongs to", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID folderId;

    @Schema(description = "Name of the task", example = "Complete project documentation")
    private String name;

    @Schema(description = "Detailed description of the task",
            example = "Update all API documentation with the latest endpoints and examples")
    private String description;

    @JsonProperty("due_date")
    @Schema(description = "Due date and time for the task in ISO-8601 format",
            example = "2025-12-31T23:59:59+03:00")
    private ZonedDateTime dueDate;

    @Schema(description = "Current status of the task", example = "IN_PROGRESS",
            allowableValues = {"NO_STARTED", "IN_PROGRESS", "DONE"})
    private String status;

    @Schema(description = "Priority level of the task", example = "HIGH",
            allowableValues = {"LOW", "MEDIUM", "HIGH"})
    private String priority;

    @JsonProperty("created_at")
    @Schema(description = "Timestamp when the task was created in ISO-8601 format",
            example = "2025-01-01T12:00:00+03:00")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    @Schema(description = "Timestamp when the task was last updated in ISO-8601 format",
            example = "2025-01-02T15:30:00+03:00")
    private ZonedDateTime updatedAt;

    @JsonProperty("total_time")
    @Schema(description = "Total time spent on this task across all sessions in ISO-8601 duration format",
            example = "PT2H30M")
    private Duration totalTime;

    @Schema(description = "List of all work sessions associated with this task")
    private List<SessionSummaryWebResponse> sessions;


}

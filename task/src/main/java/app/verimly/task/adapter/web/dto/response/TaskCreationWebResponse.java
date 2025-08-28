package app.verimly.task.adapter.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Response object for the task newly created.")
public class TaskCreationWebResponse {

    @Schema(
            description = "ID of the task created newly.",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID id;

    @Schema(
            description = "ID of the user which owns the task.",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @JsonProperty("owner_id")
    private UUID ownerId;

    @Schema(
            description = "ID of the folder where the task will be created",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @JsonProperty("folder_id")
    private UUID folderId;


    @Schema(
            description = "Name of the task",
            example = "Complete project documentation"
    )
    private String name;

    @Schema(
            description = "Detailed description of the task",
            example = "Update all API documentation with the latest endpoint changes"
    )
    private String description;


    @Schema(
            description = "Due date and time for the task in ISO-8601 format",
            example = "2025-12-31T23:59:59+03:00"
    )
    @JsonProperty("due_date")
    private ZonedDateTime dueDate;

    @Schema(
            description = "Priority level of the task",
            example = "HIGH",
            allowableValues = {"LOW", "MEDIUM", "HIGH"}
    )
    private String priority;


    @Schema(
            description = "Status of the task",
            example = "NOT_STARTED",
            allowableValues = {"NOT_STARTED", "IN_PROGRESS", "DONE"}
    )
    private String status;
}

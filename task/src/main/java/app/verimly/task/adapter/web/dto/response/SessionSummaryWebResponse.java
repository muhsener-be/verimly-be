package app.verimly.task.adapter.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Summary information about a work session, including timing and status details")
public class SessionSummaryWebResponse {

    @Schema(description = "Unique identifier of the session", 
            example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID id;
    
    @JsonProperty("owner_id")
    @Schema(description = "ID of the user who owns this session", 
            example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID ownerId;
    
    @JsonProperty("task_id")
    @Schema(description = "ID of the task this session is associated with", 
            example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID taskId;
    
    @Schema(description = "Name or description of the session", 
            example = "Morning coding session")
    private String name;
    
    @Schema(description = "Current status of the session", 
            example = "COMPLETED",
            allowableValues = {"RUNNING", "PAUSED", "FINISHED"})
    private String status;
    
    @JsonProperty("started_at")
    @Schema(description = "Timestamp when the session was started in ISO-8601 format", 
            example = "2025-01-01T09:00:00+03:00")
    private ZonedDateTime startedAt;
    
    @JsonProperty("paused_at")
    @Schema(description = "Timestamp when the session was last paused in ISO-8601 format (if applicable)", 
            example = "2025-01-01T10:30:00+03:00",
            nullable = true)
    private ZonedDateTime pausedAt;
    
    @JsonProperty("finished_at")
    @Schema(description = "Timestamp when the session was finished in ISO-8601 format (if finished)",
            example = "2025-01-01T11:30:00+03:00",
            nullable = true)
    private ZonedDateTime finishedAt;
    
    @JsonProperty("total_pause")
    @Schema(description = "Total time the session was paused in ISO-8601 duration format", 
            example = "PT30M")
    private Duration totalPause;

    @JsonProperty("total_time")
    @Schema(description = "Total active time spent in this session in ISO-8601 duration format (excluding pauses)", 
            example = "PT2H")
    private Duration totalTime;


}



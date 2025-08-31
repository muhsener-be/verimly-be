package app.verimly.task.adapter.web.dto.aggregate;

import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TaskWithSessionsWebResponse {

    private UUID id;

    @JsonProperty("owner_id")
    private UUID ownerId;

    @JsonProperty("folder_id")
    private UUID folderId;

    private String name;

    private String description;

    @JsonProperty("due_date")
    private ZonedDateTime dueDate;

    private String status;


    private String priority;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;

    @JsonProperty("total_time")
    private Duration totalTime;

    private List<SessionSummaryWebResponse> sessions;


}

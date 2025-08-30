package app.verimly.task.adapter.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SessionSummaryWebResponse {


    private UUID id;
    @JsonProperty("owner_id")
    private UUID ownerId;
    @JsonProperty("task_id")
    private UUID taskId;
    private String name;
    private String status;
    @JsonProperty("started_at")
    private ZonedDateTime startedAt;
    @JsonProperty("paused_at")
    private ZonedDateTime pausedAt;
    @JsonProperty("finished_at")
    private ZonedDateTime finishedAt;
    @JsonProperty("total_pause")
    private Duration totalPause;

    @JsonProperty("total_time")
    private Duration totalTime;


}



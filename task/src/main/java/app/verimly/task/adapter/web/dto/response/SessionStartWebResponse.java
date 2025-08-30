package app.verimly.task.adapter.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SessionStartWebResponse {

    private UUID id;

    @JsonProperty("owner_id")
    private UUID ownerId;

    @JsonProperty("task_id")
    private UUID taskId;

    private String name;

    @JsonProperty("started_at")
    private ZonedDateTime startedAt;

    private String status;


}

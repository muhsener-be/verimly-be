package app.verimly.task.adapter.web.dto.request;

import app.verimly.task.domain.vo.task.TaskId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StartSessionForTaskWebRequest {

    private String name;

    @JsonProperty("task_id")
    private UUID taskId;
}

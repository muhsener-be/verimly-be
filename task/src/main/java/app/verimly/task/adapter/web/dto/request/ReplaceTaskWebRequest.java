package app.verimly.task.adapter.web.dto.request;

import app.verimly.task.domain.vo.task.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReplaceTaskWebRequest {

    private String name;

    private String description;

    @JsonProperty("due_date")
    private ZonedDateTime dueDate;

    private TaskStatus status;


    private String priority;

}

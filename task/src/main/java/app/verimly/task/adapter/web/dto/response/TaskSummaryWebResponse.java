package app.verimly.task.adapter.web.dto.response;

import app.verimly.task.domain.vo.task.TaskStatus;
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
public class TaskDetailsWebResponse {

    private UUID id;

    @JsonProperty("owner_id")
    private UUID ownerId;

    @JsonProperty("folder_id")
    private UUID folderId;

    private String name;

    private String description;

    @JsonProperty("due_date")
    private ZonedDateTime dueDate;

    private TaskStatus status;


    private String priority;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
}

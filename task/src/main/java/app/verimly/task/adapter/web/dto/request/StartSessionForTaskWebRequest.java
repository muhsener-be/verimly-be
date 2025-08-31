package app.verimly.task.adapter.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Request object for starting a new session for a task")
public class StartSessionForTaskWebRequest {

    @NotBlank(message = "session-name.not-blank")
    @Schema(
        description = "Name of the session",
        example = "Morning Work Session",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @JsonProperty("task_id")
    @NotNull(message = "session.task-id.not-null")
    @Schema(
        description = "Unique identifier of the task to start a session for",
        example = "123e4567-e89b-12d3-a456-426614174000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID taskId;
}

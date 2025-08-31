package app.verimly.task.adapter.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Request object for changing session status")
public class ChangeSessionStatusWebRequest {

    @NotNull(message = "Session status changing action cannot be null.")
    @Schema(
        description = "Action to perform on the session. Valid values: PAUSE, RESUME, FINISH",
        example = "PAUSE",
        requiredMode = Schema.RequiredMode.REQUIRED,
        allowableValues = {"PAUSE", "RESUME", "FINISH"}
    )
    private String action;
}

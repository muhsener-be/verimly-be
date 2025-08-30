package app.verimly.task.adapter.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChangeSessionStatusWebRequest {

    @NotNull(message = "Session status changing action cannot be null.")
    private String action;
}

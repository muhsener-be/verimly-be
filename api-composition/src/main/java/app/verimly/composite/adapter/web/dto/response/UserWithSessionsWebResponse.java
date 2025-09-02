package app.verimly.composite.adapter.web.dto.response;

import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import app.verimly.user.adapter.web.dto.response.UserDetailsWebResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO containing user details along with their active sessions.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Response containing user details and their active sessions")
public class UserWithSessionsWebResponse {

    @JsonProperty("user")
    @Schema(description = "Detailed information about the user")
    private UserDetailsWebResponse user;

    @JsonProperty("active_sessions")
    @ArraySchema(schema = @Schema(description = "List of user's active sessions", implementation = SessionSummaryWebResponse.class))
    private List<SessionSummaryWebResponse> activeSessions;
}

package app.verimly.composite.application.usecase.fetch_user_profile_and_active_session;

import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.user.application.dto.UserDetailsData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserWithSessionsResponse {

    private UserDetailsData user;
    private List<SessionSummaryData> activeSessions;
}

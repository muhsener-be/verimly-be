package app.verimly.composite.application.usecase.fetch_user_profile_and_active_session;

import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.ports.in.SessionApplicationService;
import app.verimly.user.application.dto.UserDetailsData;
import app.verimly.user.application.ports.in.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FetchUserDataAndSessionQueryHandler {

    private final UserApplicationService userApplicationService;
    private final SessionApplicationService sessionApplicationService;

    @Transactional
    public UserWithSessionsResponse handle() {
        UserDetailsData userDetailsData = userApplicationService.fetchUserDetails();
        List<SessionSummaryData> sessionSummaryData = sessionApplicationService.fetchActiveSessions();

        return new UserWithSessionsResponse(userDetailsData, sessionSummaryData);

    }
}

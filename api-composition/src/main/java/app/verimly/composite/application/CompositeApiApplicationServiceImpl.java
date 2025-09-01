package app.verimly.composite.application;

import app.verimly.composite.application.ports.in.CompositeApiApplicationService;
import app.verimly.composite.application.usecase.fetch_user_profile_and_active_session.FetchUserDataAndSessionQueryHandler;
import app.verimly.composite.application.usecase.fetch_user_profile_and_active_session.UserWithSessionsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CompositeApiApplicationServiceImpl implements CompositeApiApplicationService {

    private final FetchUserDataAndSessionQueryHandler handler;


    @Override
    public UserWithSessionsResponse fetchUserAndActiveSessions() {
        return handler.handle();
    }
}

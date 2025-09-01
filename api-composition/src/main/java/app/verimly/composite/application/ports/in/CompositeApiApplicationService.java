package app.verimly.composite.application.ports.in;

import app.verimly.composite.application.usecase.fetch_user_profile_and_active_session.UserWithSessionsResponse;

public interface CompositeApiApplicationService {

    UserWithSessionsResponse fetchUserAndActiveSessions();
}

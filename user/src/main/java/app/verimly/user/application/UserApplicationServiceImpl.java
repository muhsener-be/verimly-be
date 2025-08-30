package app.verimly.user.application;

import app.verimly.user.application.dto.UserDetailsData;
import app.verimly.user.application.ports.in.UserApplicationService;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.CreateUserCommandHandler;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.application.usecase.query.view.FetchUserDetailsQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final CreateUserCommandHandler createUserCommandHandler;
    private final FetchUserDetailsQueryHandler fetchUserDetailsQueryHandler;

    @Override
    public UserCreationResponse create(CreateUserCommand command) {
        return createUserCommandHandler.handle(command);
    }

    @Override
    public UserDetailsData fetchUserDetails() {
        return fetchUserDetailsQueryHandler.handle();
    }
}

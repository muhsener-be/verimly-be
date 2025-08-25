package app.verimly.user.application;

import app.verimly.user.application.ports.in.UserApplicationService;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.CreateUserCommandHandler;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final CreateUserCommandHandler createUserCommandHandler;

    @Override
    public UserCreationResponse create(CreateUserCommand command) {
        return createUserCommandHandler.handle(command);
    }
}

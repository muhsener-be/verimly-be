package app.verimly.user.application.ports.in;

import app.verimly.user.application.dto.UserDetailsData;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import jakarta.validation.constraints.NotNull;

public interface UserApplicationService {

    UserCreationResponse create(@NotNull CreateUserCommand command);

    UserDetailsData fetchUserDetails();
}

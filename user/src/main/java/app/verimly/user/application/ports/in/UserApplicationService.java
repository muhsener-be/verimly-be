package app.verimly.user.application.ports.in;

import app.verimly.user.application.dto.UserDetailsData;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;

public interface UserApplicationService {

    UserCreationResponse create(CreateUserCommand command);

    UserDetailsData fetchUserDetails();
}

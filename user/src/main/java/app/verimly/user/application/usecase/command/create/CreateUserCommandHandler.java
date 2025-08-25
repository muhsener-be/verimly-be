package app.verimly.user.application.usecase.command.create;

import app.verimly.user.application.event.UserCreatedApplicationEvent;
import app.verimly.user.application.mapper.UserAppMapper;
import app.verimly.user.application.ports.out.SecurityPort;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.repository.UserWriteRepository;
import app.verimly.user.domain.vo.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final UserWriteRepository userWriteRepository;
    private final UserAppMapper userAppMapper;
    private final SecurityPort securityPort;


    public UserCreationResponse handle(CreateUserCommand command) {
        User user = createUser(command);

        User savedUser = persistUser(user);

        UserCreatedApplicationEvent event = prepareEvent(savedUser);
        publishEvent(event);

        return prepareResponse(savedUser);

    }

    protected User createUser(CreateUserCommand command) {
        Password encryptedPassword = encryptPassword(command.password());
        return User.create(command.name(), command.email(), encryptedPassword);
    }

    protected Password encryptPassword(Password password) {
        return securityPort.encrypt(password);
    }

    protected User persistUser(User user) {
        return userWriteRepository.save(user);
    }

    protected UserCreatedApplicationEvent prepareEvent(User savedUser) {
        return UserCreatedApplicationEvent.of(savedUser);
    }

    protected void publishEvent(UserCreatedApplicationEvent event) {
        eventPublisher.publishEvent(event);
    }

    protected UserCreationResponse prepareResponse(User user) {
        return userAppMapper.toUserCreationResponse(user);
    }
}

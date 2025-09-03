package app.verimly.user.application.usecase.command.create;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.user.application.event.UserCreatedApplicationEvent;
import app.verimly.user.application.exception.DuplicateEmailException;
import app.verimly.user.application.exception.UserBusinessException;
import app.verimly.user.application.exception.UserSystemException;
import app.verimly.user.application.mapper.UserAppMapper;
import app.verimly.user.application.ports.out.security.EncryptionException;
import app.verimly.user.application.ports.out.security.SecurityPort;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.exception.UserDomainException;
import app.verimly.user.domain.repository.UserDataAccessException;
import app.verimly.user.domain.repository.UserWriteRepository;
import app.verimly.user.domain.vo.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final UserWriteRepository userWriteRepository;
    private final UserAppMapper userAppMapper;
    private final SecurityPort securityPort;


    @Transactional
    public UserCreationResponse handle(CreateUserCommand command) {
        assert command != null : "'command' cannot be null to handle create user command.";
        validateBusinessRules(command);

        User user = createUser(command);
        User savedUser = persistUser(user);

        UserCreatedApplicationEvent event = prepareEvent(savedUser);
        publishEvent(event);

        return prepareResponse(savedUser);

    }

    private void validateBusinessRules(CreateUserCommand command) {
        ensureEmailIsUnique(command.email());
    }

    private void ensureEmailIsUnique(Email email) {
        boolean exists = userWriteRepository.existsByEmail(email);
        if (exists)
            throw new DuplicateEmailException(email);
    }

    protected User createUser(CreateUserCommand command) {
        Password encryptedPassword = encryptPassword(command.password());


        try {
            return User.create(command.name(), command.email(), encryptedPassword);
        } catch (UserDomainException ex) {
            throw new UserBusinessException(ex.getErrorMessage(), ex.getMessage(), ex);
        }


    }

    protected Password encryptPassword(Password password) {
        try {
            return securityPort.encrypt(password);
        } catch (EncryptionException e) {
            throw new UserSystemException(e.getErrorMessage(), e.getMessage(), e);
        }

    }

    protected User persistUser(User user) {
        try {
            return userWriteRepository.save(user);
        } catch (DuplicateEmailException duplicateEmailException) {
            throw duplicateEmailException;
        } catch (UserDataAccessException exception) {
            throw new UserSystemException(exception.getErrorMessage(), exception.getMessage(), exception);
        }
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

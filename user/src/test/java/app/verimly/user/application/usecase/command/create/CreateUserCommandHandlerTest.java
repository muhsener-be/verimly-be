package app.verimly.user.application.usecase.command.create;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.user.application.event.UserCreatedApplicationEvent;
import app.verimly.user.application.mapper.UserAppMapper;
import app.verimly.user.application.ports.out.EncryptionException;
import app.verimly.user.application.ports.out.SecurityPort;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.exception.UserDomainException;
import app.verimly.user.domain.repository.UserDataAccessException;
import app.verimly.user.domain.repository.UserWriteRepository;
import app.verimly.user.domain.vo.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserCommandHandlerTest {

    UserTestData DATA = UserTestData.getInstance();
    CreateUserCommand command = DATA.createUserCommand();
    User user = DATA.user();
    UserCreationResponse userCreationResponse = DATA.userCreationResponse();
    UserCreatedApplicationEvent event = UserCreatedApplicationEvent.of(user);
    Password encryptedPassword = DATA.password().encrypted();
    Password rawPassword = DATA.password().raw();


    @Mock
    ApplicationEventPublisher eventPublisher;
    @Mock
    UserAppMapper userAppMapper;

    @Mock
    UserWriteRepository userWriteRepository;

    @Mock
    SecurityPort securityPort;

    @InjectMocks
    CreateUserCommandHandler createUserCommandHandler;

    @Test
    void setup_is_ok() {
        assertNotNull(userWriteRepository);
        assertNotNull(createUserCommandHandler);
        assertNotNull(eventPublisher);
        assertNotNull(userAppMapper);
        assertNotNull(securityPort);
    }


    @Test
    void handle_happy_path() {
        when(securityPort.encrypt(command.password())).thenReturn(encryptedPassword);
        when(userWriteRepository.save(any(User.class))).thenReturn(user);
        doNothing().when(eventPublisher).publishEvent(any(UserCreatedApplicationEvent.class));
        when(userAppMapper.toUserCreationResponse(user)).thenReturn(userCreationResponse);

        UserCreationResponse response = createUserCommandHandler.handle(command);


        assertEquals(userCreationResponse, response);
        verify(securityPort).encrypt(command.password());
        verify(userWriteRepository).save(any(User.class));
        verify(eventPublisher).publishEvent(any(UserCreatedApplicationEvent.class));
        verify(userAppMapper).toUserCreationResponse(user);
    }


    @Test
    void handle_whenProblemInEncryption_thenDoesNotCreateUser() {
        doThrow(EncryptionException.class).when(securityPort).encrypt(command.password());

        assertThrows(EncryptionException.class,
                () -> createUserCommandHandler.handle(command));

        verify(securityPort).encrypt(command.password());
        verifyNoInteractions(userWriteRepository, eventPublisher, userAppMapper);
    }

    @Test
    void handle_whenProblemInCreatingUser_thenDoesNotPersistUser() {
        when(securityPort.encrypt(command.password())).thenReturn(rawPassword);


        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> createUserCommandHandler.handle(command));

        ErrorMessage actualErrorMessage = exception.getErrorMessage();

        assertEquals(User.Errors.NOT_ENCRYPTED_PASSWORD, actualErrorMessage);
        verify(securityPort).encrypt(command.password());
        verifyNoInteractions(userWriteRepository, eventPublisher, userAppMapper);
    }

    @Test
    void handle_whenProblemInPersistence_thenDoesNotPublishEvent() {
        when(securityPort.encrypt(command.password())).thenReturn(encryptedPassword);
        doThrow(UserDataAccessException.class).when(userWriteRepository).save(any(User.class));

        assertThrows(UserDataAccessException.class,
                () -> createUserCommandHandler.handle(command));

        verify(securityPort).encrypt(command.password());
        verify(userWriteRepository).save(any(User.class));
        verifyNoInteractions(eventPublisher, userAppMapper);
    }

}

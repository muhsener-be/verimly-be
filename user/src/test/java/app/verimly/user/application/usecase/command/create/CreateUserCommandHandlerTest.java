package app.verimly.user.application.usecase.command.create;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.user.application.event.UserCreatedApplicationEvent;
import app.verimly.user.application.exception.DuplicateEmailException;
import app.verimly.user.application.exception.UserBusinessException;
import app.verimly.user.application.exception.UserSystemException;
import app.verimly.user.application.mapper.UserAppMapper;
import app.verimly.user.application.ports.out.EncryptionException;
import app.verimly.user.application.ports.out.SecurityPort;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.repository.UserDataAccessException;
import app.verimly.user.domain.repository.UserWriteRepository;
import app.verimly.user.domain.vo.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
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

    private final Email COMMAND_EMAIL = command.email();


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


    @BeforeEach
    public void setup() {
        lenient().when(userWriteRepository.existsByEmail(COMMAND_EMAIL)).thenReturn(false);
        lenient().when(securityPort.encrypt(command.password())).thenReturn(encryptedPassword);
        lenient().when(userWriteRepository.save(any(User.class))).thenReturn(user);
        lenient().doNothing().when(eventPublisher).publishEvent(any(UserCreatedApplicationEvent.class));
        lenient().when(userAppMapper.toUserCreationResponse(user)).thenReturn(userCreationResponse);


    }


    @Test
    void handle_withValidCommand_shouldCreateAndReturnResponse() {

        // ACT
        UserCreationResponse response = createUserCommandHandler.handle(command);


        // ASSERT
        assertEquals(userCreationResponse, response);
        verify(userWriteRepository).existsByEmail(command.email());
        verify(securityPort).encrypt(command.password());
        verify(userWriteRepository).save(any(User.class));
        verify(eventPublisher).publishEvent(any(UserCreatedApplicationEvent.class));
        verify(userAppMapper).toUserCreationResponse(user);
    }

    @Test
    @DisplayName("HAPPY PATH")
    void handle_whenEmailAlreadyExists_thenThrowsDuplicateEmailException() {
        // ARRANGE
        when(userWriteRepository.existsByEmail(COMMAND_EMAIL)).thenReturn(true);

        // ACT
        Executable action = () -> createUserCommandHandler.handle(command);


        // ASSERT
        DuplicateEmailException exception = assertThrows(DuplicateEmailException.class, action);
        assertEquals(COMMAND_EMAIL, exception.getEmail());
        verify(userWriteRepository).existsByEmail(COMMAND_EMAIL);
        verifyNoInteractions(securityPort, eventPublisher, userAppMapper);
        verify(userWriteRepository, times(0)).save(any(User.class));

    }

    @Test
    @DisplayName("ENCRYPTION PROBLEM")
    void handle_whenProblemOccursDuringEncryption_thenThrowsUserSystemException() {
        // ARRANGE
        EncryptionException encryptionException = new EncryptionException("Encryption failed.");
        doThrow(encryptionException).when(securityPort).encrypt(command.password());

        // ACT
        Executable action = () -> createUserCommandHandler.handle(command);

        // ASSERT
        UserSystemException systemException = assertThrows(UserSystemException.class, action);
        assertNotNull(systemException.getErrorMessage());
        verify(securityPort).encrypt(command.password());
        verify(userWriteRepository).existsByEmail(COMMAND_EMAIL);
        verify(userWriteRepository, times(0)).save(any(User.class));
        verifyNoInteractions(eventPublisher, userAppMapper);
    }

    @Test
    @DisplayName("DOMAIN INVARIANT PROBLEM")
    void handle_whenDomainInvariantViolated_thenThrowsUserBusinessException() {
        // ARRANGE
        when(securityPort.encrypt(command.password())).thenReturn(rawPassword);

        // ACT
        Executable action = () -> createUserCommandHandler.handle(command);

        //ASSERT
        UserBusinessException exception = assertThrows(UserBusinessException.class, action);
        ErrorMessage actualErrorMessage = exception.getErrorMessage();
        assertEquals(User.Errors.NOT_ENCRYPTED_PASSWORD, actualErrorMessage);
        verify(securityPort).encrypt(command.password());
        verify(userWriteRepository).existsByEmail(COMMAND_EMAIL);
        verify(userWriteRepository, times(0)).save(any(User.class));
        verifyNoInteractions(eventPublisher, userAppMapper);
    }


    @Test
    @DisplayName("PERSISTENCE PROBLEM")
    void handle_whenProblemOccursDuringPersistence_thenThrowsUserSystemException() {
        // ARRANGE
        doThrow(new UserDataAccessException("Cart curt")).when(userWriteRepository).save(any(User.class));

        //ACT
        Executable action = () -> createUserCommandHandler.handle(command);

        //ASSERT
        assertThrows(UserSystemException.class, action);
        verify(securityPort).encrypt(command.password());
        verify(userWriteRepository).existsByEmail(COMMAND_EMAIL);
        verify(userWriteRepository).save(any(User.class));
        verifyNoInteractions(eventPublisher, userAppMapper);
    }

}

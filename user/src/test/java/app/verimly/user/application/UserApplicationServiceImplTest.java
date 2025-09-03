package app.verimly.user.application;

import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.CreateUserCommandHandler;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.data.UserTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserApplicationServiceImplTest {

    @Mock
    CreateUserCommandHandler createUserCommandHandler;

    private UserTestData DATA = UserTestData.getInstance();
    private CreateUserCommand createUserCommand = DATA.createUserCommand();
    private UserCreationResponse mockCreationResponse = Mockito.mock(UserCreationResponse.class);

    @InjectMocks
    UserApplicationServiceImpl applicationService;


    @BeforeEach
    public void setup() {

    }

    @Test
    void setup_is_ok() {
        assertNotNull(applicationService);
        assertNotNull(createUserCommandHandler);
    }

    @Test
    void create_happy_path() {
        when(createUserCommandHandler.handle(createUserCommand)).thenReturn(mockCreationResponse);

        UserCreationResponse response = applicationService.create(createUserCommand);

        assertEquals(mockCreationResponse, response);
        verify(createUserCommandHandler).handle(createUserCommand);
    }

    @Test
    void create_whenCommandIsNull_thenThrowsIllegalArgumentException() {
        createUserCommand = null;

        assertThrows(IllegalArgumentException.class, () -> applicationService.create(createUserCommand));


        verifyNoInteractions(createUserCommandHandler);
    }
}

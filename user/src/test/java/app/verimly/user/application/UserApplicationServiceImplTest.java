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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserApplicationServiceImplTest {

    @Mock
    CreateUserCommandHandler createUserCommandHandler;

    private final UserTestData DATA = new UserTestData();
    private final CreateUserCommand createUserCommand = DATA.createUserCommand();
    private final UserCreationResponse mockCreationResponse = Mockito.mock(UserCreationResponse.class);

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
}

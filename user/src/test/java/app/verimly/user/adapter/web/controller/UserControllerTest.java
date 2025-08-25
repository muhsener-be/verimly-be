package app.verimly.user.adapter.web.controller;


import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.user.adapter.web.dto.request.CreateUserWebRequest;
import app.verimly.user.adapter.web.dto.response.UserCreationWebResponse;
import app.verimly.user.adapter.web.mapper.UserWebMapper;
import app.verimly.user.application.ports.in.UserApplicationService;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.repository.UserDataAccessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {


    @Mock
    private UserWebMapper userWebMapper;

    @Mock
    private UserApplicationService userApplicationService;

    @InjectMocks
    private UserController userController;

    UserTestData data = UserTestData.getInstance();
    CreateUserWebRequest webRequest = data.createUserWebRequest();
    UserCreationWebResponse webResponse = data.userCreationWebResponse();
    CreateUserCommand command = data.createUserCommand();
    UserCreationResponse response = data.userCreationResponse();


    @Test
    void should_setup_is_ok() {
        assertNotNull(userController);
    }

    @Test
    void create_happy_path() {
        when(userWebMapper.toCreateUserCommand(webRequest)).thenReturn(command);
        when(userApplicationService.create(command)).thenReturn(response);
        when(userWebMapper.toUserCreationWebResponse(response)).thenReturn(webResponse);

        UserCreationWebResponse actualResponse = userController.create(webRequest);

        assertEquals(webResponse, actualResponse);
        verify(userWebMapper).toCreateUserCommand(webRequest);
        verify(userApplicationService).create(command);
        verify(userWebMapper).toUserCreationWebResponse(response);
    }

    @Test
    void create_whenProblemInMapping_throwsInvalidDomainObjectException() {
        doThrow(InvalidDomainObjectException.class).when(userWebMapper).toCreateUserCommand(webRequest);

        assertThrows(InvalidDomainObjectException.class,
                () -> userController.create(webRequest));

        verify(userWebMapper).toCreateUserCommand(webRequest);
        verifyNoInteractions(userApplicationService);
        verify(userWebMapper, times(0)).toUserCreationWebResponse(any());
    }


    @Test
    void create_whenProblemInApplicationService_thenDoesNotReturnResponse() {
        when(userWebMapper.toCreateUserCommand(webRequest)).thenReturn(command);
        doThrow(UserDataAccessException.class).when(userApplicationService).create(command);

        assertThrows(UserDataAccessException.class,
                () -> userController.create(webRequest));

        verify(userWebMapper).toCreateUserCommand(webRequest);
        verify(userApplicationService).create(command);
        verify(userWebMapper, times(0)).toUserCreationWebResponse(any());
    }


}

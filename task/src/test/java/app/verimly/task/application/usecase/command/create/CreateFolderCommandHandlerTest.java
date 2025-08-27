package app.verimly.task.application.usecase.command.create;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.*;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.event.FolderCreatedApplicationEvent;
import app.verimly.task.application.mapper.FolderAppMapper;
import app.verimly.task.application.ports.out.security.FolderActions;
import app.verimly.task.application.ports.out.security.TaskAuthenticationService;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.exception.FolderDomainException;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFolderCommandHandlerTest {


    public static final FolderDomainException FOLDER_DOMAIN_EXCEPTION = new FolderDomainException();
    public static final TaskDataAccessException TASK_DATA_ACCESS_EXCEPTION = new TaskDataAccessException();
    @Mock
    TaskAuthenticationService authN;

    @Mock
    TaskAuthorizationService authZ;

    @Mock
    FolderWriteRepository repository;

    @Mock
    ApplicationEventPublisher eventPublisher;

    @Mock
    FolderAppMapper mapper;

    @InjectMocks
    CreateFolderCommandHandler handler;
    CreateFolderCommandHandler spyHandler;

    public static final Action CREATE_ACTION = FolderActions.CREATE;
    public static final AuthResource FOLDER_RESOURCE = null;
    public static final AuthenticationRequiredException AUTHENTICATION_REQUIRED_EXCEPTION = new AuthenticationRequiredException("Test exception.");
    FolderTestData DATA = FolderTestData.getInstance();
    CreateFolderCommand command;
    Principal principal;
    Folder folder;
    FolderCreatedApplicationEvent event;
    FolderCreationResponse response;
    Email email;
    UserId ownerId;


    @BeforeEach
    void setup() {
        command = DATA.createFolderCommand();
        email = Email.of("email@email.com");
        ownerId = UserId.random();
        principal = AuthenticatedPrincipal.of(ownerId, email);
        folder = DATA.folder();
        event = Mockito.mock(FolderCreatedApplicationEvent.class);
        response = Mockito.mock(FolderCreationResponse.class);

        spyHandler = Mockito.spy(handler);

        lenient().when(authN.getCurrentPrincipal()).thenReturn(principal);
        lenient().doNothing().when(authZ).authorize(principal, CREATE_ACTION, FOLDER_RESOURCE);
        lenient().doReturn(folder).when(spyHandler).createFolder(principal, command);
        lenient().when(repository.save(folder)).thenReturn(folder);
        lenient().doReturn(event).when(spyHandler).prepareEvent(principal, folder);
        lenient().doNothing().when(eventPublisher).publishEvent(event);
        lenient().when(mapper.toFolderCreationResponse(folder)).thenReturn(response);

    }

    @Test
    void setup_is_ok() {
        assertNotNull(authN);
        assertNotNull(authZ);
        assertNotNull(repository);
        assertNotNull(eventPublisher);
        assertNotNull(handler);
    }

    @Test
    void handle_whenValidCommand_thenReturnsValidResponse() {

        FolderCreationResponse actualResponse = spyHandler.handle(command);

        assertEquals(response, actualResponse);
        verify(authN).getCurrentPrincipal();
        verify(authZ).authorize(principal, CREATE_ACTION, FOLDER_RESOURCE);
        verify(repository).save(folder);
        verify(eventPublisher).publishEvent(event);
        verify(mapper).toFolderCreationResponse(folder);
    }

    @Test
    void handle_whenProblemDuringAuthorizing_thenThrowsSecurityException() {
        doThrow(AUTHENTICATION_REQUIRED_EXCEPTION).when(authZ).authorize(principal, CREATE_ACTION, FOLDER_RESOURCE);

        Executable handle = () -> spyHandler.handle(command);


        assertThrows(SecurityException.class, handle);

        verify(authN).getCurrentPrincipal();
        verify(authZ).authorize(principal, CREATE_ACTION, FOLDER_RESOURCE);
        verifyNoInteractions(repository, event, mapper);
    }

    @Test
    void handle_whenFolderInvariantsViolated_thenThrowsFolderDomainException() {
        doThrow(FOLDER_DOMAIN_EXCEPTION).when(spyHandler).createFolder(principal, command);

        Executable handle = () -> spyHandler.handle(command);

        assertThrows(FolderDomainException.class, handle);
        verify(authN).getCurrentPrincipal();
        verify(authZ).authorize(principal, CREATE_ACTION, FOLDER_RESOURCE);
        verifyNoInteractions(repository, event, mapper);
//        verify(repository).save(folder);
//        verify(eventPublisher).publishEvent(event);
//        verify(mapper).toFolderCreationResponse(folder);
    }

    @Test
    void handle_whenProblemDuringPersisting_thenThrowsTaskDataAccessException() {
        doThrow(TASK_DATA_ACCESS_EXCEPTION).when(repository).save(folder);

        Executable handle = () -> spyHandler.handle(command);

        assertThrows(TaskDataAccessException.class, handle);
        verify(authN).getCurrentPrincipal();
        verify(authZ).authorize(principal, CREATE_ACTION, FOLDER_RESOURCE);
        verify(repository).save(folder);
        verifyNoInteractions(event, mapper);
    }
}
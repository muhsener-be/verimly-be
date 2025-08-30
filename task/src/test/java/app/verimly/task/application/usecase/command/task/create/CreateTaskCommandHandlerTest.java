package app.verimly.task.application.usecase.command.task.create;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.*;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.event.TaskCreatedApplicationEvent;
import app.verimly.task.application.exception.FolderNotFoundException;
import app.verimly.task.application.mapper.TaskAppMapper;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.application.ports.out.security.context.CreateTaskContext;
import app.verimly.task.data.SecurityTestData;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.input.TaskCreationDetails;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.service.TaskDomainService;
import app.verimly.task.domain.vo.folder.FolderId;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTaskCommandHandlerTest {

    public static final AuthenticationRequiredException AUTHENTICATION_REQUIRED_EXCEPTION = new AuthenticationRequiredException("Test exception");
    @Mock
    private AuthenticationService authN;
    @Mock
    private TaskAuthorizationService authZ;
    @Mock
    private TaskDomainService taskDomainService;
    @Mock
    private FolderWriteRepository folderWriteRepository;
    @Mock
    private TaskWriteRepository repository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private TaskAppMapper mapper;

    SecurityTestData SECURITY_DATA = SecurityTestData.getInstance();
    TaskTestData TASK_DATA = TaskTestData.getInstance();
    FolderTestData FOLDER_DATA = FolderTestData.getInstance();
    Principal principal;
    CreateTaskCommand command;
    Folder folder;
    Task task;
    TaskCreatedApplicationEvent event;
    FolderId folderId;
    Action action;
    TaskCreationDetails details;
    UserId ownerId;
    TaskCreationResponse response;
    CreateTaskContext resource;


    @InjectMocks
    CreateTaskCommandHandler handler;

    CreateTaskCommandHandler spyHandler;

    @BeforeEach
    public void setup() {
        spyHandler = Mockito.spy(handler);
        principal = SECURITY_DATA.authenticatedPrincipal();
        command = TASK_DATA.createTaskCommand();
        folder = FOLDER_DATA.folderWithFullFields();
        task = TASK_DATA.task();
        event = TASK_DATA.taskCreatedApplicationEvent();
        folderId = command.folderId();
        action = TaskActions.CREATE;
        details = TASK_DATA.taskCreationDetails();
        ownerId = principal.getId();
        response = TASK_DATA.taskCreationResponse();
        resource = SECURITY_DATA.createTaskContext();

        lenient().when(authN.getCurrentPrincipal()).thenReturn(principal);
        lenient().doNothing().when(authZ).authorizeCreateTask(any(Principal.class), any(CreateTaskContext.class));
        lenient().when(folderWriteRepository.findById(folderId)).thenReturn(Optional.ofNullable(folder));
        lenient().when(mapper.toTaskCreationDetails(command, ownerId)).thenReturn(details);
        lenient().when(taskDomainService.createTask(folder, details)).thenReturn(task);
        lenient().when(repository.save(task)).thenReturn(task);
        lenient().doReturn(event).when(spyHandler).prepareEvent(principal, task);
        lenient().doNothing().when(eventPublisher).publishEvent(event);
        lenient().when(mapper.toTaskCreationResponse(task)).thenReturn(response);

    }

    @Test
    void shouldSetupOK() {
        assertNotNull(authN);
        assertNotNull(authZ);
        assertNotNull(taskDomainService);
        assertNotNull(folderWriteRepository);
        assertNotNull(repository);
        assertNotNull(eventPublisher);
        assertNotNull(mapper);
        assertNotNull(handler);
        assertNotNull(spyHandler);
    }

    @Test
    void handle_whenValidCommand_thenReturnValidResponse() {

        TaskCreationResponse actual = spyHandler.handle(command);

        assertEquals(response, actual);
        verify(authN).getCurrentPrincipal();
        verifyAuthZIsCalled();
        verify(folderWriteRepository).findById(folderId);
        verify(mapper).toTaskCreationDetails(command, ownerId);
        verify(taskDomainService).createTask(folder, details);
        verify(repository).save(task);
        verify(spyHandler).prepareEvent(principal, task);
        verify(eventPublisher).publishEvent(event);
        verify(mapper).toTaskCreationResponse(task);
    }

    private void verifyAuthZIsCalled() {
        ArgumentCaptor<Principal> argumentCaptor = ArgumentCaptor.forClass(Principal.class);
        verify(authZ).authorizeCreateTask(argumentCaptor.capture(), any(CreateTaskContext.class));
        assertEquals(principal,argumentCaptor.getValue());
    }


    @Test
    void handle_whenNullCommand_thenThrowsIllegalArgumentException() {
        command = null;

        assertThrowsIllegalArgumentException();
    }


    @Test
    void handle_whenProblemDuringAuthorizing_thenThrowsSecurityException() {
        doThrow(AUTHENTICATION_REQUIRED_EXCEPTION).when(authZ).authorizeCreateTask(any(Principal.class), any(CreateTaskContext.class));

        assertThrowsException(SecurityException.class);
        verify(authN).getCurrentPrincipal();
        verifyAuthZIsCalled();
        verifyNoInteractions(taskDomainService, folderWriteRepository, repository, eventPublisher, mapper);
    }

    @Test
    void handle_whenFolderNotFound_thenThrowsFolderNotFoundException() {
        when(folderWriteRepository.findById(folderId)).thenReturn(Optional.empty());

        assertThrowsException(FolderNotFoundException.class);
        verify(authN).getCurrentPrincipal();
        verifyAuthZIsCalled();
        verify(folderWriteRepository).findById(folderId);
        verifyNoInteractions(taskDomainService, repository, eventPublisher, mapper);
    }

    @Test
    void handle_whenProblemDuringFetchingFolder_thenThrowsTaskDataAccessException() {
        doThrow(TaskDataAccessException.class).when(folderWriteRepository).findById(folderId);

        assertThrowsException(TaskDataAccessException.class);
        verify(authN).getCurrentPrincipal();
        verifyAuthZIsCalled();
        verify(folderWriteRepository).findById(folderId);
        verifyNoInteractions(taskDomainService, repository, eventPublisher, mapper);


    }

    @Test
    void handle_whenProblemDuringCreationTask_thenThrowsTaskDomainException() {
        doThrow(TaskDomainException.class).when(taskDomainService).createTask(folder, details);

        assertThrowsException(TaskDomainException.class);
        verify(authN).getCurrentPrincipal();
        verifyAuthZIsCalled();
        verify(folderWriteRepository).findById(folderId);
        verify(mapper).toTaskCreationDetails(command, ownerId);
        verify(taskDomainService).createTask(folder, details);
        verifyNoInteractions(eventPublisher, repository);
        verify(mapper, times(0)).toTaskCreationResponse(task);
    }


    @Test
    void handle_whenProblemDuringPersistingTask_thenThrowsTaskDataAccessException() {
        doThrow(TaskDataAccessException.class).when(repository).save(task);

        assertThrowsException(TaskDataAccessException.class);
        verify(authN).getCurrentPrincipal();
        verifyAuthZIsCalled();
        verify(folderWriteRepository).findById(folderId);
        verify(mapper).toTaskCreationDetails(command, ownerId);
        verify(taskDomainService).createTask(folder, details);
        verify(repository).save(task);
        verifyNoInteractions(eventPublisher);
        verify(mapper, times(0)).toTaskCreationResponse(task);
    }


    private void assertThrowsIllegalArgumentException() {
        assertThrowsException(IllegalArgumentException.class);
    }

    private void assertThrowsException(Class<? extends Throwable> exClass) {
        assertThrows(exClass, getHandleExecutable());
    }

    private @NotNull Executable getHandleExecutable() {
        return () -> spyHandler.handle(command);
    }
}

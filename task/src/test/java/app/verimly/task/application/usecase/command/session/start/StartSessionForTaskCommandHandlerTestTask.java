package app.verimly.task.application.usecase.command.session.start;

import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.TaskAbstractUnitTest;
import app.verimly.task.application.exception.TaskNotFoundException;
import app.verimly.task.application.mapper.SessionAppMapper;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.StartSessionContext;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.exception.TimeSessionDomainException;
import app.verimly.task.domain.input.SessionCreationDetails;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.repository.TimeSessionWriteRepository;
import app.verimly.task.domain.service.TimeSessionDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartSessionForTaskCommandHandlerTestTask extends TaskAbstractUnitTest {

    @Mock
    private AuthenticationService authN;
    @Mock
    private TaskAuthorizationService authZ;
    @Mock
    private TaskWriteRepository taskRepository;
    @Mock
    private TimeSessionWriteRepository sessionRepository;
    @Mock
    private SessionAppMapper mapper;
    @Mock
    private TimeSessionDomainService domainService;

    @InjectMocks
    StartSessionForTaskCommandHandler handler;


    TimeSession session;
    StartSessionForTaskCommand command;
    Task task;
    Principal principal;
    StartSessionContext context;
    SessionCreationDetails details;
    SessionStartResponse response;

    @BeforeEach
    public void setup() {
        principal = AUTHENTICATED_PRINCIPAL;
        command = SESSION_TEST_DATA.startSessionCommand();
        task = TASK_TEST_DATA.taskWithId(command.taskId());
        context = SECURITY_TEST_DATA.startSessionContextWithId(task.getId());
        details = SESSION_TEST_DATA.sessionCreationDetailsWithOwnerId(principal.getId());
        session = SESSION_TEST_DATA.sessionWithTaskIdAndOwnerId(task.getId(), principal.getId());
        response = SESSION_TEST_DATA.sessionStartResponse(session.getId(), session.getOwnerId(), session.getTaskId());

        lenient().when(authN.getCurrentPrincipal()).thenReturn(principal);
        lenient().doNothing().when(authZ).authorizeStartSession(any(Principal.class), any(StartSessionContext.class));
        lenient().when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        lenient().when(mapper.toSessionCreationDetails(principal.getId(), command)).thenReturn(details);
        lenient().when(sessionRepository.save(session)).thenReturn(session);
        lenient().when(mapper.toSessionStartResponse(session)).thenReturn(response);


    }

    @Test
    void handle_whenCommandIsNull_thenThrowsIllegalArgumentException() {
        command = null;

        assertThrowsIllegalArgumentException(getHandleExecutable());
    }

    @Test
    void handle_whenTaskNotFound_thenThrowsTaskNotFound() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());



        assertThrowsExceptions(TaskNotFoundException.class, getHandleExecutable());
        verify(taskRepository, times(0)).save(task);
        verifyNoInteractions( sessionRepository, mapper, domainService);
    }

    @Test
    void handle_whenAuthorizationFailed_thenThrowsSecurityException() {
        doThrow(AUTHENTICATION_REQUIRED_EXCEPTION).when(authZ).authorizeStartSession(any(Principal.class), any(StartSessionContext.class));

        assertThrowsExceptions(SecurityException.class, getHandleExecutable());

        verifyAuthZIsCalled();
        verify(taskRepository, times(0)).save(task);
        verifyNoInteractions(domainService, mapper);
    }

    @Test
    void handle_whenDomainServiceFails_thenThrowsTimeSessionDomainException() {
        TimeSessionDomainException exception = new TimeSessionDomainException("Test exception");
        doThrow(exception).when(domainService).startSessionForTask(task, details);

        assertThrowsExceptions(TimeSessionDomainException.class, getHandleExecutable());

        verify(authN).getCurrentPrincipal();
        verify(taskRepository).findById(task.getId());
        verify(domainService).startSessionForTask(task, details);
        verifyAuthZIsCalled();

        verify(sessionRepository, times(0)).save(session);
        verify(mapper, times(0)).toSessionStartResponse(session);
    }

//    @Test
//    void handle_happy_path() {
//
//        SessionStartResponse actual = handler.handle(command);
//
//        assertEquals( this.response ,actual );
//    }

    private void verifyAuthZIsCalled() {
        ArgumentCaptor<Principal> principalCaptor = ArgumentCaptor.forClass(Principal.class);

        verify(authZ).authorizeStartSession(principalCaptor.capture(), any(StartSessionContext.class));
        assertEquals(principal , principalCaptor.getValue());
    }

    private Executable getHandleExecutable() {
        return () -> handler.handle(command);
    }
}
package app.verimly.task.application.usecase.query.task.list_by_folder;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.AbstractUnitTest;
import app.verimly.task.application.mapper.TaskAppMapper;
import app.verimly.task.application.ports.out.persistence.TaskReadRepository;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.ListTasksByFolderContext;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.vo.folder.FolderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListTasksByFolderQueryHandlerTest extends AbstractUnitTest {

    @Mock
    private AuthenticationService authN;
    @Mock
    private TaskAuthorizationService authZ;
    @Mock
    private TaskReadRepository taskReadRepository;
    @Mock
    private TaskAppMapper taskAppMapper;

    @InjectMocks
    ListTasksByFolderQueryHandler handler;


    FolderId folderId;
    Principal principal;
    UserId principalId;

    @BeforeEach
    void setup() {
        folderId = FolderId.random();
        principal = AUTHENTICATED_PRINCIPAL;
        principalId = principal.getId();

        lenient().when(authN.getCurrentPrincipal()).thenReturn(principal);
        lenient().doNothing().when(authZ).authorizeListTasksByFolder(any(Principal.class), any(ListTasksByFolderContext.class));

    }


    @Test
    void shouldSetupIsOk() {
        assertNotNull(authN);
        assertNotNull(authZ);
        assertNotNull(taskReadRepository);
        assertNotNull(taskAppMapper);
        assertNotNull(handler);

    }


    @Test
    void handle_whenFolderIsNull_thenThrowsIllegalArgumentException() {
        folderId = null;

        super.assertThrowsExceptions(IllegalArgumentException.class, getHandleExecutable());
    }

    @Test
    void handle_whenPrincipalIsAnonymous_thenThrowsAuthenticationRequiredException() {
        doThrow(AUTHENTICATION_REQUIRED_EXCEPTION).when(authZ).authorizeListTasksByFolder(any(Principal.class), any(ListTasksByFolderContext.class));

        assertThrowsExceptions(AuthenticationRequiredException.class, getHandleExecutable());

        verifyAuthZIsCalled();
        verifyNoInteractions(taskReadRepository, taskAppMapper);
    }

    @Test
    void when_ProblemDuringFetchingTasks_thenThrowsTaskDataAccessException() {
        doThrow(TASK_DATA_ACCESS_EXCEPTION).when(taskReadRepository).fetchTaskInFolderForUser(principalId, folderId);

        assertThrows(TaskDataAccessException.class,getHandleExecutable());

        verifyAuthZIsCalled();
        verify(taskReadRepository).fetchTaskInFolderForUser(principalId,folderId);

    }

    private void verifyAuthZIsCalled() {
        ArgumentCaptor<Principal> argumentCaptor = ArgumentCaptor.forClass(Principal.class);
        verify(authZ).authorizeListTasksByFolder(argumentCaptor.capture(), any(ListTasksByFolderContext.class));
        assertEquals(principal, argumentCaptor.getValue());
    }

    Executable getHandleExecutable() {
        return () -> handler.handle(folderId);
    }


}
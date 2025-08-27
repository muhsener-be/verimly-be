package app.verimly.task.application.usecase.query;

import app.verimly.commons.core.security.Action;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.dto.FolderSummaryData;
import app.verimly.task.application.mapper.FolderAppMapper;
import app.verimly.task.application.ports.out.persistence.FolderReadRepository;
import app.verimly.task.application.ports.out.persistence.FolderSummaryProjection;
import app.verimly.task.application.ports.out.security.FolderActions;
import app.verimly.task.application.ports.out.security.TaskAuthenticationService;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.repository.TaskDataAccessException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListFoldersQueryHandlerTest {
    public static final Action ACTION = FolderActions.LIST;
    @Mock
    private TaskAuthenticationService authN;
    @Mock
    private TaskAuthorizationService authZ;
    @Mock
    private FolderReadRepository repository;
    @Mock
    private FolderAppMapper mapper;

    @InjectMocks
    ListFoldersQueryHandler queryHandler;

    FolderTestData DATA = FolderTestData.getInstance();
    Principal principal;
    List<FolderSummaryProjection> projections;
    List<FolderSummaryData> summaryData;
    AuthenticationRequiredException authenticationRequiredException;




    @BeforeEach
    void setup() {
        principal = DATA.authenticatedPrincipal();
        projections = new ArrayList<>();
        summaryData = new ArrayList<>();
        authenticationRequiredException = DATA.authenticationRequiredException();

        lenient().when(authN.getCurrentPrincipal()).thenReturn(principal);
        lenient().doNothing().when(authZ).authorize(principal, ACTION, null);
        lenient().when(repository.findSummariesByOwnerId(principal.getId())).thenReturn(projections);
        lenient().when(mapper.toFolderSummaryData(projections)).thenReturn(summaryData);
    }

    @Test
    void handle_happy_path(){

        List<FolderSummaryData> response = queryHandler.handle();

        verify(authN).getCurrentPrincipal();
        verify(authZ).authorize(principal, ACTION, null);
        verify(repository).findSummariesByOwnerId(principal.getId());
        verify(mapper).toFolderSummaryData(projections);
        assertEquals(summaryData,response);

    }

    @Test
    void handle_whenProblemDuringAuthorizing_thenThrowsSecurityException() {
        doThrow(authenticationRequiredException).when(authZ).authorize(principal, ACTION, null);

        assertThrowsException(SecurityException.class);
        verify(authN).getCurrentPrincipal();
        verify(authZ).authorize(principal, ACTION, null);
        verifyNoInteractions(repository, mapper);
    }


    @Test
    void handle_whenProblemDuringFetchingFromDb_thenThrowsTaskDataAccessException() {
        doThrow(TaskDataAccessException.class).when(repository).findSummariesByOwnerId(principal.getId());

        assertThrowsException(TaskDataAccessException.class);
        verify(authN).getCurrentPrincipal();
        verify(authZ).authorize(principal, ACTION, null);
        verify(repository).findSummariesByOwnerId(principal.getId());
        verifyNoInteractions(mapper);
    }






    private void assertThrowsException(Class<? extends Throwable> exceptionClass) {
        Executable executable = getHandleExecutable();
        assertThrows(exceptionClass, executable);
    }

    private @NotNull Executable getHandleExecutable() {
        return () -> queryHandler.handle();
    }
}
package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.AbstractUnitTest;
import app.verimly.task.application.ports.out.security.context.MoveToFolderContext;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoveTaskToFolderAuthorizationRuleTest extends AbstractUnitTest {


    @Mock
    private TaskWriteRepository taskWriteRepository;
    @Mock
    private FolderWriteRepository folderWriteRepository;

    @InjectMocks
    MoveTaskToFolderAuthorizationRule rule;


    static Principal principal;
    static TaskId taskId;
    static FolderId folderId;
    static MoveToFolderContext context;
    static Task task;
    static Folder folder;

    @BeforeEach
    void setup() {
        taskId = TaskId.random();
        folderId = FolderId.random();
        principal = AUTHENTICATED_PRINCIPAL;
        context = SECURITY_TEST_DATA.moveTaskToFolderContext(taskId, folderId);
        task = TASK_TEST_DATA.taskWithIdAndOwnerId(taskId, principal.getId());
        folder = FOLDER_TEST_DATA.folderWithIdAndOwnerId(folderId, principal.getId());


        lenient().when(taskWriteRepository.findById(taskId)).thenReturn(Optional.of(task));
        lenient().when(folderWriteRepository.findById(folderId)).thenReturn(Optional.of(folder));


    }


    @Test
    void apply_whenValidArguments_thenSuccess() {
        principal = AUTHENTICATED_PRINCIPAL;

        rule.apply(principal, context);

        verify(taskWriteRepository).findById(taskId);
        verify(folderWriteRepository).findById(folderId);

    }

    @ParameterizedTest
    @MethodSource("supplyNullArgumentsToApply")
    void apply_whenArgumentsNull_thenThrowsIllegalArgumentException(Principal argumentPrincipal, MoveToFolderContext argumentContext) {
        principal = argumentPrincipal;
        context = argumentContext;

        assertThrowsExceptions(IllegalArgumentException.class, getApplyExecutable());

    }

    @Test
    void apply_whenPrincipalIsAnonymous_thenThrowsAuthenticationRequiredException() {
        principal = ANONYMOUS_PRINCIPAL;

        assertThrowsExceptions(AuthenticationRequiredException.class, getApplyExecutable());

    }

    @Test
    void apply_whenTaskNotFoundInDb_thenThrowsNoPermissionException() {
        when(taskWriteRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrowsExceptions(NoPermissionException.class, getApplyExecutable());
    }


    @Test
    void apply_whenPrincipalIsNotOwnerTheTask_thenThrowsNoPermissionException() {
        UserId randomOwnerId = UserId.random();
        task = task.toBuilder().ownerId(randomOwnerId).build();
        when(taskWriteRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertThrowsExceptions(NoPermissionException.class, getApplyExecutable());
    }

    @Test
    void apply_whenFolderNotFoundInDb_thenThrowsNoPermissionException() {
        when(folderWriteRepository.findById(folderId)).thenReturn(Optional.empty());

        assertThrowsExceptions(NoPermissionException.class, getApplyExecutable());
    }


    @Test
    void apply_whenPrincipalIsNotOwnerTheFolder_thenThrowsNoPermissionException() {
        UserId randomOwnerId = UserId.random();
        folder = folder.toBuilder().ownerId(randomOwnerId).build();
        when(folderWriteRepository.findById(folderId)).thenReturn(Optional.of(folder));

        assertThrowsExceptions(NoPermissionException.class, getApplyExecutable());
    }

    static List<Arguments> supplyNullArgumentsToApply() {
        return List.of(
                Arguments.of(null, context),
                Arguments.arguments(principal, null),
                Arguments.arguments(null, null)
        );
    }

    private Executable getApplyExecutable() {
        return () -> rule.apply(principal, context);
    }
}
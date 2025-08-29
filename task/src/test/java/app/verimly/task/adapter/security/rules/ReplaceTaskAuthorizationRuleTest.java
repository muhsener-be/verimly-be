package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.AbstractUnitTest;
import app.verimly.task.application.ports.out.security.context.ReplaceTaskContext;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import org.jetbrains.annotations.NotNull;
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

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplaceTaskAuthorizationRuleTest extends AbstractUnitTest {

    @Mock
    TaskWriteRepository repository;

    @InjectMocks
    ReplaceTaskAuthorizationRule rule;

    static Principal principal;
    static ReplaceTaskContext context;
    static TaskId taskId;
    static Task task;

    public static Stream<Arguments> supplyNullArguments() {
        return Stream.of(
                Arguments.arguments(principal, null),
                Arguments.arguments(null, context),
                Arguments.arguments(null, null)
        );
    }


    @BeforeEach
    void setup() {
        principal = AUTHENTICATED_PRINCIPAL;
        taskId = TaskId.random();
        task = TASK_TEST_DATA.taskWithIdAndOwnerId(taskId, principal.getId());
        context = SECURITY_TEST_DATA.replaceTaskContextWithTaskId(taskId);


    }

    @Test
    void apply_happy_path() {
        when(repository.findById(taskId)).thenReturn(Optional.of(task));

        rule.apply(principal, context);

        verify(repository).findById(taskId);
    }

    @ParameterizedTest
    @MethodSource("supplyNullArguments")
    void apply_whenArgumentsNull_thenThrowsIllegalArgumentException(Principal argumentPrincipal, ReplaceTaskContext argumentContext) {
        principal = argumentPrincipal;
        context = argumentContext;

        assertThrowsExceptions(IllegalArgumentException.class, getApplyExecutable());
    }

    @Test
    void apply_whenTaskNotFound_thenThrowsNoPermissionException() {
        when(repository.findById(taskId)).thenReturn(Optional.empty());

        assertThrowsExceptions(NoPermissionException.class, getApplyExecutable());
    }


    @Test
    void apply_whenNotAuthenticated_thenThrowsAuthenticationRequiredException() {
        principal = ANONYMOUS_PRINCIPAL;

        assertThrowsExceptions(AuthenticationRequiredException.class, getApplyExecutable());
    }


    @Test
    void apply_whenNotOwner_thenThrowsNoPermissionException() {
        UserId randomOwnerId = UserId.random();
        task = task.toBuilder().ownerId(randomOwnerId).build();
        when(repository.findById(taskId)).thenReturn(Optional.of(task));

        assertThrowsExceptions(NoPermissionException.class, getApplyExecutable());
    }

    private @NotNull Executable getApplyExecutable() {
        return () -> rule.apply(principal, context);
    }


}
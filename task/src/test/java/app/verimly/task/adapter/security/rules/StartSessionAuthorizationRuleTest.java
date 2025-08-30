package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.AbstractUnitTest;
import app.verimly.task.application.ports.out.security.context.StartSessionContext;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StartSessionAuthorizationRuleTest extends AbstractUnitTest {
    static Principal principal;
    static StartSessionContext context;

    @Mock
    TaskWriteRepository repository;

    @InjectMocks
    StartSessionAuthorizationRule rule;

    public static Stream<Arguments> provideNullArgumentToApplyRule() {
        return Stream.of(
                Arguments.arguments(principal, null),
                Arguments.arguments(null, context),
                Arguments.arguments(null, null)
        );
    }

    @BeforeEach
    void setup() {
        principal = AUTHENTICATED_PRINCIPAL;
        context = StartSessionContext.createWithTaskId(TaskId.random());
    }

    @ParameterizedTest
    @MethodSource("provideNullArgumentToApplyRule")
    void apply_whenArgumentsNull_thenThrowsIllegalArgumentExcepton(Principal argPrincipal, StartSessionContext argContext) {
        principal = argPrincipal;
        context = argContext;

        assertThrowsExceptions(IllegalArgumentException.class, getApplyExecutable());
    }


    @Test
    void apply_whenNotAuthenticated_thenThrows() {
        principal = ANONYMOUS_PRINCIPAL;

        assertThrowsExceptions(AuthenticationRequiredException.class, getApplyExecutable());
    }

    @Test
    void apply_whenTaskOwnerNotFound_thenThrows() {
        principal = AUTHENTICATED_PRINCIPAL;
        when(repository.findOwnerOf(context.getTaskId())).thenReturn(Optional.empty());

        assertThrowsExceptions(NoPermissionException.class, getApplyExecutable());
    }

    @Test
    void apply_whenNotOwnerOfTask_thenThrows() {
        principal = AUTHENTICATED_PRINCIPAL;
        UserId randomOwnerId = UserId.random();
        when(repository.findOwnerOf(context.getTaskId())).thenReturn(Optional.of(randomOwnerId));

        assertThrowsExceptions(NoPermissionException.class, getApplyExecutable());
    }

    @Test
    void apply_whenValid_thenSuccess() {
        principal = AUTHENTICATED_PRINCIPAL;
        when(repository.findOwnerOf(context.getTaskId())).thenReturn(Optional.of(principal.getId()));

        assertDoesNotThrowsException(getApplyExecutable());
    }

    private @NotNull Executable getApplyExecutable() {
        return () -> rule.apply(principal, context);
    }
}
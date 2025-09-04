package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.PermissionViolation;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.TaskAbstractUnitTest;
import app.verimly.task.application.ports.out.security.context.CreateTaskContext;
import app.verimly.task.domain.repository.FolderWriteRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CreateTaskAuthorizationRuleTest extends TaskAbstractUnitTest {


    CreateTaskContext context;
    Principal principal;

    @Mock
    FolderWriteRepository repository;

    @InjectMocks
    CreateTaskAuthorizationRule rule;


    @BeforeEach
    public void setup() {
        principal = AUTHENTICATED_PRINCIPAL;
        context = SECURITY_TEST_DATA.createTaskContext();
    }


    @Test
    @DisplayName("Setup is OK")
    void should_setup_is_ok() {
        assertNotNull(rule);
    }

    @Test
    @DisplayName("Null Principal --> IllegalArgumentException")
    void apply_whenPrincipalIsNull_thenThrowsIllegalArgumentException() {
        principal = null;

        assertThrowsIllegalArgumentException(getExecutable());
    }

    @Test
    @DisplayName("Null Context --> IllegalArgumentException")
    void apply_whenContextIsNull_thenThrowsIllegalArgumentException() {
        context = null;

        assertThrowsIllegalArgumentException(getExecutable());
    }


    @Test
    @DisplayName("Anonymous Principal -> AuthenticationRequiredException")
    void apply_whenAnonymousPrincipal_thenThrowsAuthenticationRequiredException() {
        principal = ANONYMOUS_PRINCIPAL;

        assertThrows(AuthenticationRequiredException.class, getExecutable());
    }

    @Test
    @DisplayName("Not Owner of Folder -> NoPermissionException")
    void apply_whenNotOwnerOfFolder_thenThrowsNoPermissionException() {
        when(repository.findOwnerOf(context.getFolderId())).thenReturn(Optional.of(UserId.random()));

        NoPermissionException exception = assertThrows(NoPermissionException.class, getExecutable());


        PermissionViolation violation = exception.getViolation();
        assertNotNull(violation);
        System.out.println(exception.getMessage());
    }


    @Test
    @DisplayName("Happy Path")
    void apply_whenAuthenticated_noProblem() {
        principal = AUTHENTICATED_PRINCIPAL;
        when(repository.findOwnerOf(context.getFolderId())).thenReturn(Optional.of(principal.getId()));

        assertDoesNotThrow(getExecutable());

        verify(repository).findOwnerOf(context.getFolderId());

    }


    private @NotNull Executable getExecutable() {
        return () -> rule.apply(principal, context);
    }


}
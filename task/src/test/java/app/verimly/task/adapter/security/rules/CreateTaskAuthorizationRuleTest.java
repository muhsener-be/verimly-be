package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.security.Action;
import app.verimly.commons.core.security.AuthResource;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.data.SecurityTestData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = CreateTaskAuthorizationRule.class)
class CreateTaskAuthorizationRuleTest {

    SecurityTestData DATA = SecurityTestData.getInstance();

    Principal AUTHENTICATED;
    Principal ANONYMOUS;
    AuthResource resource;
    Principal principal;

    @Autowired
    CreateTaskAuthorizationRule rule;


    @BeforeEach
    public void setup() {
        AUTHENTICATED = DATA.authenticatedPrincipal();
        ANONYMOUS = DATA.anonymousPrincipal();
        resource = DATA.taskResource();
    }

    @Test
    void apply_whenAuthenticated_noProblem() {
        principal = AUTHENTICATED;

        assertDoesNotThrowException();
    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(rule);
    }

    @Test
    void apply_whenPrincipalIsNull_thenThrowsIllegalArgumentException() {
        Principal principal = null;

        assertThrowsIllegalArgumentException(principal, resource);
    }

    @Test
    void apply_whenResourceNotInstanceOfTaskResource_thenThrowsIllegalArgumentException() {
        resource = DATA.folderResource();

        assertThrowsIllegalArgumentException(AUTHENTICATED, resource);
    }

    @Test
    void apply_whenAnonymousPrincipal_thenThrowsAuthenticationRequiredException() {

        assertThrowsException(AuthenticationRequiredException.class, ANONYMOUS, resource);
    }

    @Test
    void apply_whenResourceIsNull_noProblem() {
        principal = AUTHENTICATED;
        resource = null;

        assertDoesNotThrowException();
    }

    @Test
    void getSupportedAction_shouldReturnsTaskActionsCreate() {
        Action supportedAction = rule.getSupportedAction();

        assertEquals(TaskActions.CREATE, supportedAction);
    }

    private void assertDoesNotThrowException() {
        assertDoesNotThrow(getApplyExecutable());
    }

    private Executable getApplyExecutable() {
        return () -> rule.apply(principal, resource);
    }

    private void assertThrowsException(Class<? extends Throwable> exceptionClass, Principal principal, AuthResource resource) {
        assertThrows(exceptionClass,
                getApplyExecutable(principal, resource));
    }

    private @NotNull Executable getApplyExecutable(Principal principal, AuthResource resource) {
        return () -> rule.apply(principal, resource);
    }


    private void assertThrowsIllegalArgumentException(Principal principal, AuthResource resource) {
        assertThrowsException(IllegalArgumentException.class, principal, resource);
    }
}
package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.context.CreateTaskContext;
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

    private static final SecurityTestData SECURITY_TEST_DATA = SecurityTestData.getInstance();

    Principal AUTHENTICATED;
    Principal ANONYMOUS;
    CreateTaskContext context;
    Principal principal;

    @Autowired
    CreateTaskAuthorizationRule rule;


    @BeforeEach
    public void setup() {
        AUTHENTICATED = SECURITY_TEST_DATA.authenticatedPrincipal();
        ANONYMOUS = SECURITY_TEST_DATA.anonymousPrincipal();
        context = SECURITY_TEST_DATA.createTaskContext();
    }


    @Test
    void should_setup_is_ok() {
        assertNotNull(rule);
    }

    @Test
    void apply_whenPrincipalIsNull_thenThrowsIllegalArgumentException() {
        Principal principal = null;

        assertThrowsIllegalArgumentException(principal, context);
    }


    @Test
    void apply_whenAnonymousPrincipal_thenThrowsAuthenticationRequiredException() {

        assertThrowsException(AuthenticationRequiredException.class, ANONYMOUS, context);
    }


    @Test
    void apply_whenResourceIsNull_noProblem() {
        principal = AUTHENTICATED;
        context = null;

        assertDoesNotThrowException();
    }

    @Test
    void apply_whenAuthenticated_noProblem() {
        principal = AUTHENTICATED;

        assertDoesNotThrowException();
    }










    private void assertDoesNotThrowException() {
        assertDoesNotThrow(getApplyExecutable());
    }

    private Executable getApplyExecutable() {
        return () -> rule.apply(principal, context);
    }

    private void assertThrowsException(Class<? extends Throwable> exceptionClass, Principal principal, CreateTaskContext context) {
        assertThrows(exceptionClass,
                getApplyExecutable(principal, context));
    }

    private @NotNull Executable getApplyExecutable(Principal principal, CreateTaskContext context) {
        return () -> rule.apply(principal, context);
    }


    private void assertThrowsIllegalArgumentException(Principal principal, CreateTaskContext context) {
        assertThrowsException(IllegalArgumentException.class, principal, context);
    }
}
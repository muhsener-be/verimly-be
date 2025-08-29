package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.context.CreateFolderContext;
import app.verimly.task.data.SecurityTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = CreateFolderAuthorizationRule.class)
class CreateFolderAuthorizationRuleTest {

    private static final SecurityTestData SECURITY_TEST_DATA = SecurityTestData.getInstance();

    private CreateFolderContext authorizationContext;
    private CreateFolderContext mockResource;
    private Principal authenticatedPrincipal;
    private Principal anonPrincipal;

    @Autowired
    CreateFolderAuthorizationRule rule;

    @BeforeEach
    void setup() {
        authorizationContext = SECURITY_TEST_DATA.createFolderContext();
        mockResource = Mockito.mock(CreateFolderContext.class);
        anonPrincipal = SECURITY_TEST_DATA.anonymousPrincipal();
        authenticatedPrincipal = SECURITY_TEST_DATA.authenticatedPrincipal();

    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(rule);
    }





    @Test
    void apply_whenPrincipalIsNull_thenThrowsIllegalArgumentException() {
        anonPrincipal = null;

        Executable executable = () -> rule.apply(anonPrincipal, mockResource);

        assertThrows(IllegalArgumentException.class, executable);

    }

    @Test
    void apply_whenAnonymousPrincipal_thenThrowsAuthenticationRequiredException() {

        Executable executable = () -> rule.apply(anonPrincipal, authorizationContext);


        AuthenticationRequiredException exception = assertThrows(AuthenticationRequiredException.class, executable);
        assertEquals(AuthenticationRequiredException.ERROR_MESSAGE, exception.getErrorMessage());
    }

    @Test
    void apply_whenResourceIsNull_doesNotThrowException() {
        authorizationContext = null;

        Executable apply = () -> rule.apply(authenticatedPrincipal, null);

        assertDoesNotThrow(apply);
    }


}
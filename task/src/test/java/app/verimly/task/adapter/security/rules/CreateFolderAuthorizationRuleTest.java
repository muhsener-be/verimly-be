package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.Action;
import app.verimly.commons.core.security.AuthResource;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.action.FolderActions;
import app.verimly.task.data.SecurityTestData;
import app.verimly.task.domain.vo.folder.FolderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = CreateFolderAuthorizationRule.class)
class CreateFolderAuthorizationRuleTest {

    private static final SecurityTestData TEST_DATA = SecurityTestData.getInstance();

    private AuthResource authResource;
    private AuthResource mockResource;
    private Principal authenticatedPrincipal;
    private Principal anonPrincipal;

    @Autowired
    CreateFolderAuthorizationRule rule;

    @BeforeEach
    void setup() {
        authResource = TEST_DATA.folderResource();
        mockResource = Mockito.mock(AuthResource.class);
        anonPrincipal = TEST_DATA.anonymousPrincipal();
        authenticatedPrincipal = TEST_DATA.authenticatedPrincipal();

    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(rule);
    }


    @Test
    void apply_whenResourceNotInstanceOfFolderResource_thenThrowsIllegalStateException() {


        Executable executable = () -> rule.apply(anonPrincipal, mockResource);

        IllegalStateException exception = assertThrows(IllegalStateException.class, executable);

        System.out.println(exception.getMessage());

    }


    @Test
    void apply_whenPrincipalIsNull_thenThrowsIllegalArgumentException() {
        anonPrincipal = null;

        Executable executable = () -> rule.apply(anonPrincipal, mockResource);

        assertThrows(IllegalArgumentException.class, executable);

    }

    @Test
    void apply_whenAnonymousPrincipal_thenThrowsAuthenticationRequiredException() {

        Executable executable = () -> rule.apply(anonPrincipal, authResource);


        AuthenticationRequiredException exception = assertThrows(AuthenticationRequiredException.class, executable);
        assertEquals(AuthenticationRequiredException.ERROR_MESSAGE, exception.getErrorMessage());
    }

    @Test
    void apply_whenResourceIsNull_doesNotThrowException() {
        authResource = null;

        Executable apply = () -> rule.apply(authenticatedPrincipal, null);

        assertDoesNotThrow(apply);
    }

    @Test
    void getSupportedAction_returnsFolderActionsCREATE() {

        Action actual = rule.getSupportedAction();

        assertEquals(FolderActions.CREATE, actual);
    }
}
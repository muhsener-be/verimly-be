package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthResource;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.resource.TaskResource;
import app.verimly.task.data.SecurityTestData;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.repository.FolderWriteRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTasksByFolderAuthorizationRuleTest {
    private static final SecurityTestData SECURITY_DATA = SecurityTestData.getInstance();
    private static final TaskTestData TASK_DATA = TaskTestData.getInstance();
    private static final FolderTestData FOLDER_DATA = FolderTestData.getInstance();


    Principal AUTHENTICATED;
    Principal ANONYMOUS;
    Principal principal;
    AuthResource authResource;
    @Mock
    FolderWriteRepository folderWriteRepository;

    @InjectMocks
    ListTasksByFolderAuthorizationRule rule;


    @BeforeEach
    void setup() {
        AUTHENTICATED = SECURITY_DATA.authenticatedPrincipal();
        ANONYMOUS = SECURITY_DATA.anonymousPrincipal();
        authResource = SECURITY_DATA.taskResource();
    }


    @Test
    void apply_whenPrincipalIsNull_thenThrowsIllegalArgumentException() {
        principal = null;

        assertThrowsIllegalArgumentException();
    }

    @Test
    void apply_whenResourceIsNull_thenThrowsIllegalArgumentException() {
        authResource = null;

        assertThrowsIllegalArgumentException();
    }


    @Test
    void apply_whenResourceIsNotInstanceTaskResource_thenThrowsIllegalArgumentException() {
        authResource = SECURITY_DATA.folderResource();

        assertThrowsIllegalArgumentException();
    }


    @Test
    void apply_whenFolderIdInResourceIsNull_thenThrowsIllegalArgumentException() {
        authResource = ((TaskResource) authResource).withFolderId(null);

        assertThrowsIllegalArgumentException();
    }

    @Test
    void apply_whenAnonymousUser_thenThrowsAuthenticationRequiredException() {
        principal = ANONYMOUS;

        assertThrowsException(AuthenticationRequiredException.class);
    }

    @Test
    void apply_whenPrincipalIsNotOwnerOfTheFolder_thenThrowNoPermissionException() {
        principal = AUTHENTICATED;
        UserId differentUserId = UserId.random();
        authResource = ((TaskResource) authResource).withOwnerId(differentUserId);

        assertThrowsException(NoPermissionException.class);
    }

    @Test
    void apply_whenValidArgument_thenSuccess() {
        principal = AUTHENTICATED;
        when(folderWriteRepository.findOwnerOf(((TaskResource) authResource).folderId())).thenReturn(principal.getId());


        assertDoesNotThrowsException();

    }

    private void assertDoesNotThrowsException() {
        assertDoesNotThrow(getApplyExecutable());
    }

    private void assertThrowsIllegalArgumentException() {
        assertThrowsException(IllegalArgumentException.class);
    }


    void assertThrowsException(Class<? extends Throwable> eClass) {
        assertThrows(eClass, getApplyExecutable());
    }

    private @NotNull Executable getApplyExecutable() {
        return () -> rule.apply(principal, authResource);
    }


}
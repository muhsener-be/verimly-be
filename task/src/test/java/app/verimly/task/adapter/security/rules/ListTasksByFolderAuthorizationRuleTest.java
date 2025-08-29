package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.AbstractUnitTest;
import app.verimly.task.application.ports.out.security.context.ListTasksByFolderContext;
import app.verimly.task.data.SecurityTestData;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.vo.folder.FolderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTasksByFolderAuthorizationRuleTest extends AbstractUnitTest {
    private static final SecurityTestData SECURITY_DATA = SecurityTestData.getInstance();
    private static final FolderTestData FOLDER_DATA = FolderTestData.getInstance();


    FolderId folderId = FOLDER_DATA.folderId();
    Principal principal;
    ListTasksByFolderContext context;
    @Mock
    FolderWriteRepository folderWriteRepository;

    @InjectMocks
    ListTasksByFolderAuthorizationRule rule;


    @BeforeEach
    void setup() {
        principal = AUTHENTICATED_PRINCIPAL;
        context = SECURITY_DATA.listTasksByFolderContext(folderId);
    }


    @Test
    void apply_whenPrincipalIsNull_thenThrowsIllegalArgumentException() {
        principal = null;

        assertThrowsIllegalArgumentException();
    }

    @Test
    void apply_whenResourceIsNull_thenThrowsIllegalArgumentException() {
        context = null;

        assertThrowsIllegalArgumentException();
    }


    @Test
    void apply_whenAnonymousUser_thenThrowsAuthenticationRequiredException() {
        principal = ANONYMOUS_PRINCIPAL;

        super.assertThrowsExceptions(AuthenticationRequiredException.class, getApplyExecutable());
    }

    @Test
    void apply_whenPrincipalIsNotOwnerOfTheFolder_thenThrowNoPermissionException() {
        UserId randomOwnerID = UserId.random();
        when(folderWriteRepository.findOwnerOf(folderId)).thenReturn(Optional.of(randomOwnerID));

        assertThrowsExceptions(NoPermissionException.class, getApplyExecutable());
    }

    @Test
    void apply_whenValidArgument_thenSuccess() {
        principal = AUTHENTICATED_PRINCIPAL;
        when(folderWriteRepository.findOwnerOf(folderId)).thenReturn(Optional.of(principal.getId()));


        super.assertDoesNotThrowsException(getApplyExecutable());

    }


    private void assertThrowsIllegalArgumentException() {
        super.assertThrowsExceptions(IllegalArgumentException.class, getApplyExecutable());
    }


    private Executable getApplyExecutable() {
        return () -> rule.apply(principal, context);
    }


}
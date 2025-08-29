package app.verimly.task.data;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AnonymousPrincipal;
import app.verimly.commons.core.security.AuthenticatedPrincipal;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.resource.FolderResource;
import app.verimly.task.application.ports.out.security.resource.TaskResource;
import app.verimly.task.domain.vo.folder.FolderId;
import org.jetbrains.annotations.NotNull;

public class SecurityTestData {

    private static final SecurityTestData INSTANCE = new SecurityTestData();

    public static SecurityTestData getInstance() {
        return INSTANCE;
    }

    public Principal authenticatedPrincipal() {
        return AuthenticatedPrincipal.of(getUserId(), getEmail());
    }

    private static Email getEmail() {
        return Email.of("random@email.com");
    }

    private static UserId getUserId() {
        return UserId.random();
    }

    public Principal anonymousPrincipal() {
        return new AnonymousPrincipal();
    }

    public FolderResource folderResource() {
        return new FolderResource(getFolderId(), getUserId());
    }

    public TaskResource taskResource() {
        return new TaskResource(getUserId(), getFolderId());
    }

    private static @NotNull FolderId getFolderId() {
        return FolderId.random();
    }
}

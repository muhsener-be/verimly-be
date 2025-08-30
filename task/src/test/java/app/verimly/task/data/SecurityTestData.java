package app.verimly.task.data;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AnonymousPrincipal;
import app.verimly.commons.core.security.AuthenticatedPrincipal;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.context.*;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;
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


    private static @NotNull FolderId getFolderId() {
        return FolderId.random();
    }

    public CreateFolderContext createFolderContext() {
        return new CreateFolderContext();
    }

    public ListFoldersContext listFoldersContext() {
        return new ListFoldersContext();
    }


    public ListTasksByFolderContext listTasksByFolderContext(FolderId folderId) {
        return ListTasksByFolderContext.createWithFolderId(folderId);
    }

    public CreateTaskContext createTaskContext() {
        return new CreateTaskContext();
    }

    public MoveToFolderContext moveTaskToFolderContext(TaskId taskId, FolderId folderId) {
        return MoveToFolderContext.createWithTaskIdAndNewFolderId(taskId, folderId);
    }

    public ReplaceTaskContext replaceTaskContextWithTaskId(TaskId taskId) {
        return ReplaceTaskContext.createWithTaskId(taskId);
    }

    public StartSessionContext startSessionContextWithId(TaskId id) {
        return StartSessionContext.createWithTaskId(id);
    }
}

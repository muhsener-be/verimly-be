package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.*;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.application.ports.out.security.context.MoveToFolderContext;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MoveTaskToFolderAuthorizationRule implements AuthorizationRule<MoveToFolderContext> {

    private final TaskWriteRepository taskWriteRepository;
    private final FolderWriteRepository folderWriteRepository;

    @Override
    @Transactional
    public void apply(Principal principal, MoveToFolderContext context) {
        checkInputs(principal, context);

        applyRule(principal, context);
    }

    private void checkInputs(Principal principal, MoveToFolderContext context) {
        Assert.notNull(principal, "principal cannot be null to authorize move to folder context");
        Assert.notNull(context, "context cannot be null to authorize move to folder context");
    }

    private void applyRule(Principal principal, MoveToFolderContext context) {
        ensurePrincipalIsAuthenticated(principal);
        ensurePrincipalIsOwnerOfTheTask(principal.getId(), context.getTaskId());
        ensurePrincipalIsOwnerOfTheFolder(principal.getId(), context.getNewFolderId());
    }

    private void ensurePrincipalIsAuthenticated(Principal principal) {
        if (!(principal instanceof AuthenticatedPrincipal))
            throw new AuthenticationRequiredException("Authentication is required to move task to folder.");
    }

    private void ensurePrincipalIsOwnerOfTheTask(UserId principalId, TaskId taskId) throws NoPermissionException {
        Task task = taskWriteRepository.findById(taskId).orElseThrow(() -> new NoPermissionException(principalId, TaskActions.MOVE_TO_FOLDER));
        if (!Objects.equals(principalId, task.getOwnerId()))
            throw new NoPermissionException(principalId, TaskActions.MOVE_TO_FOLDER);
    }


    private void ensurePrincipalIsOwnerOfTheFolder(UserId principalId, FolderId newFolderId) {
        Folder folder = folderWriteRepository.findById(newFolderId).orElseThrow(() -> new NoPermissionException(principalId, TaskActions.MOVE_TO_FOLDER));
        if (!Objects.equals(principalId, folder.getOwnerId()))
            throw new NoPermissionException(principalId, TaskActions.MOVE_TO_FOLDER);
    }


}

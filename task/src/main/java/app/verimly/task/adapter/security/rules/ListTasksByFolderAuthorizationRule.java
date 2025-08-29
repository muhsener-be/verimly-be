package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.*;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.application.ports.out.security.resource.TaskResource;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ListTasksByFolderAuthorizationRule implements AuthorizationRule {

    private final FolderWriteRepository folderWriteRepository;

    @Override
    public void apply(Principal principal, AuthResource resource) {
        ensureInputsAreValid(principal, resource);
        applyRule(principal, resource);
    }

    private void ensureInputsAreValid(Principal principal, AuthResource resource) {
        Assert.notNull(principal, "principal cannot be null");
        Assert.notNull(resource, "resource cannot be null in ListTasksByFolderAuthorizationRule");
        Assert.instanceOf(resource, TaskResource.class, "AuthResource must be instance of TaskResource class in %s ".formatted(this.getClass().getSimpleName()));
        TaskResource taskResource = (TaskResource) resource;
        FolderId folderId = taskResource.folderId();
        Assert.notNull(folderId, "To apply rule for listing tasks by folder, folderId is required, but found 'null'");

    }

    private void applyRule(Principal principal, AuthResource resource) {
        TaskResource taskResource = (TaskResource) resource;
        FolderId folderId = taskResource.folderId();

        ensurePrincipalIsAuthenticated(principal);
        ensurePrincipalIsOwnerOfTheFolder(principal, folderId);

    }

    private void ensurePrincipalIsAuthenticated(Principal principal) {
        if (!(principal instanceof AuthenticatedPrincipal auh))
            throw new AuthenticationRequiredException("Authentication is required to list tasks");
    }

    private void ensurePrincipalIsOwnerOfTheFolder(Principal principal, FolderId folderId) {
        UserId ownerIdOfTheFolder = fetchOwnerIdOfTheFolder(folderId);
        if (!Objects.equals(principal.getId(), ownerIdOfTheFolder))
            throw new NoPermissionException(principal, getSupportedAction());
    }

    private UserId fetchOwnerIdOfTheFolder(FolderId folderId) {
        return folderWriteRepository.findOwnerOf(folderId);
    }


    @Override
    public Action getSupportedAction() {
        return TaskActions.LIST_BY_FOLDER;
    }
}

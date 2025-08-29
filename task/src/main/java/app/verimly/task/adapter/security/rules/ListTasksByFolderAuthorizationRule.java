package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.*;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.application.ports.out.security.context.ListTasksByFolderContext;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ListTasksByFolderAuthorizationRule implements AuthorizationRule<ListTasksByFolderContext> {

    private final FolderWriteRepository folderWriteRepository;

    @Override
    public void apply(Principal principal, ListTasksByFolderContext context) {
        ensureInputsAreValid(principal, context);
        applyRule(principal, context);
    }

    private void ensureInputsAreValid(Principal principal, ListTasksByFolderContext context) {
        Assert.notNull(principal, "principal cannot be null");
        Assert.notNull(context, "context cannot be null in ListTasksByFolderAuthorizationRule");


    }

    private void applyRule(Principal principal, ListTasksByFolderContext context) {
        FolderId folderId = context.getFolderId();

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
            throw new NoPermissionException(principal, TaskActions.LIST_BY_FOLDER);
    }

    private UserId fetchOwnerIdOfTheFolder(FolderId folderId) {
        return folderWriteRepository.findOwnerOf(folderId);
    }


}

package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.application.ports.out.security.context.ListTasksByFolderContext;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ListTasksByFolderAuthorizationRule extends AbstractAuthorizationRule<ListTasksByFolderContext> {

    private final FolderWriteRepository folderWriteRepository;

    @Override
    public void apply(Principal principal, ListTasksByFolderContext context) {
        super.ensurePrincipalIsNotNull(principal);
        super.ensureContextIsNotNull(context);
        applyRule(principal, context);
    }


    private void applyRule(Principal principal, ListTasksByFolderContext context) {
        FolderId folderId = context.getFolderId();

        super.ensurePrincipalIsAuthenticated(principal, "Principal must be authenticated to list tasks by folder.");
        ensureOwnerOfTheFolder(principal, folderId);

    }


    private void ensureOwnerOfTheFolder(Principal principal, FolderId folderId) {
        UserId ownerIdOfTheFolder = fetchOwnerIdOfTheFolder(folderId);
        if (!Objects.equals(principal.getId(), ownerIdOfTheFolder))
            throw new NoPermissionException(principal, TaskActions.LIST_BY_FOLDER);
    }

    private UserId fetchOwnerIdOfTheFolder(FolderId folderId) {
        return folderWriteRepository.findOwnerOf(folderId);
    }


}

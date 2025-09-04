package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.PermissionRequirement;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.context.CreateTaskContext;
import app.verimly.task.application.ports.out.security.violation.FolderPermissionViolation;
import app.verimly.task.domain.repository.FolderWriteRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CreateTaskAuthorizationRule extends AbstractAuthorizationRule<CreateTaskContext> {

    private final FolderWriteRepository folderRepo;

    @Override
    public void apply(@NotNull Principal principal, @Nullable CreateTaskContext context) {
        super.ensurePrincipalIsNotNull(principal);
        super.ensureContextIsNotNull(context);

        applyRule(principal, context);

    }


    private void applyRule(Principal principal, CreateTaskContext context) {
        super.ensurePrincipalIsAuthenticated(principal, "Principal must be authenticated to create task.");
        ensureOwnerOfFolder(principal, context);
    }

    private void ensureOwnerOfFolder(Principal principal, CreateTaskContext context) {
        UserId ownerIdOfFolder = folderRepo.findOwnerOf(context.getFolderId()).orElse(null);
        if (!Objects.equals(principal.getId(), ownerIdOfFolder))
            throw new NoPermissionException(
                    FolderPermissionViolation.addTask(principal.getId(), context.getFolderId(), PermissionRequirement.of("OWNERSHIP"))
            );
    }


}

package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.application.ports.out.security.context.DeleteTaskContext;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DeleteTaskAuthorizationRule extends AbstractAuthorizationRule<DeleteTaskContext> {

    private final TaskWriteRepository repository;

    @Override
    public void apply(Principal principal, DeleteTaskContext context) {
        super.ensurePrincipalIsNotNull(principal);
        super.ensureContextIsNotNull(context);

        applyRule(principal, context);
    }

    private void applyRule(Principal principal, DeleteTaskContext context) {
        super.ensurePrincipalIsAuthenticated(principal, "Principal must be authenticated to delete task.");

        ensureOwnerOfTheTask(principal, context.getTaskId());

    }

    private void ensureOwnerOfTheTask(Principal principal, TaskId taskId) {
        UserId ownerId = fetchOwnerIdOfTask(principal, taskId);
        if (!Objects.equals(principal.getId(), ownerId))
            throw new NoPermissionException(principal, TaskActions.DELETE);
    }

    private UserId fetchOwnerIdOfTask(Principal principal, TaskId taskId) {
        return repository.findOwnerOf(taskId).orElseThrow(
                () -> new NoPermissionException(principal, TaskActions.DELETE)
        );
    }
}

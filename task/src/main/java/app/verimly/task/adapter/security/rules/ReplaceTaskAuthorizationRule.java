package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.application.ports.out.security.context.ReplaceTaskContext;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReplaceTaskAuthorizationRule extends AbstractAuthorizationRule<ReplaceTaskContext> {

    private final TaskWriteRepository repository;

    @Override
    public void apply(Principal principal, ReplaceTaskContext context) {
        super.ensurePrincipalIsNotNull(principal);
        super.ensureContextIsNotNull(context);

        applyRule(principal, context);
    }

    private void applyRule(Principal principal, ReplaceTaskContext context) {
        super.ensurePrincipalIsAuthenticated(principal, "Principal must be authenticated to replace task.");
        ensureOwnerOfTask(principal, context.getTaskId());
    }

    private void ensureOwnerOfTask(Principal principal, TaskId taskId) {
        UserId taskOwnerId = fetchOwnerOfTask(principal, taskId);
        if (!Objects.equals(principal.getId(), taskOwnerId))
            throw new NoPermissionException(principal, TaskActions.REPLACE);
    }

    private UserId fetchOwnerOfTask(Principal principal, TaskId taskId) {
        return repository.findById(taskId).orElseThrow(() -> new NoPermissionException(principal, TaskActions.REPLACE)).getOwnerId();
    }


}

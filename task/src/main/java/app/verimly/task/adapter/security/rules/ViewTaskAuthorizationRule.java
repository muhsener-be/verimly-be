package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.application.ports.out.security.context.ViewTaskContext;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ViewTaskAuthorizationRule extends AbstractAuthorizationRule<ViewTaskContext> {

    private final TaskWriteRepository taskRepository;

    @Override
    public void apply(Principal principal, ViewTaskContext context) {
        super.ensurePrincipalIsNotNull(principal);
        super.ensureContextIsNotNull(context);

        super.ensurePrincipalIsAuthenticated(principal);

        ensureOwnerOfTask(principal, context.getTaskId());
    }

    private void ensureOwnerOfTask(Principal principal, TaskId taskId) {
        UserId taskOwnerId = getTaskOwnerId(principal, taskId);
        if (!Objects.equals(principal.getId(), taskOwnerId))
            throw new NoPermissionException(principal, TaskActions.VIEW);
    }

    private UserId getTaskOwnerId(Principal principal, TaskId taskId) {
        return taskRepository.findOwnerOf(taskId).orElseThrow(() -> new NoPermissionException(principal, TaskActions.VIEW));
    }
}

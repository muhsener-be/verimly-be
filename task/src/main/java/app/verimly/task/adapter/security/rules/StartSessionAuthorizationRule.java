package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.Action;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.action.SessionActions;
import app.verimly.task.application.ports.out.security.context.StartSessionContext;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StartSessionAuthorizationRule extends AbstractAuthorizationRule<StartSessionContext> {

    public static final Action ACTION = SessionActions.START;
    private final TaskWriteRepository taskRepository;

    @Override
    public void apply(Principal principal, StartSessionContext context) {
        ensurePrincipalIsNotNull(principal);
        ensureContextIsNotNull(context);

        applyRule(principal, context);
    }

    private void applyRule(Principal principal, StartSessionContext context) {
        super.ensurePrincipalIsAuthenticated(principal, "Principal must be authenticated to start session for Task: " + context.getTaskId());

        ensureOwnerOfTheTask(principal, context.getTaskId());
    }

    private void ensureOwnerOfTheTask(Principal principal, TaskId taskId) {
        UserId taskOwnerId = fetchOwnerOfTask(principal, taskId);
        if (!Objects.equals(principal.getId(), taskOwnerId))
            throw new NoPermissionException(principal, ACTION);
    }

    private UserId fetchOwnerOfTask(Principal principal, TaskId taskId) {
        return taskRepository.findOwnerOf(taskId).orElseThrow(() -> new NoPermissionException(principal, ACTION));
    }
}

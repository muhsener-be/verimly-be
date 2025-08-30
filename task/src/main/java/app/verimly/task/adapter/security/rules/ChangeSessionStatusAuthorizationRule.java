package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.Action;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.action.SessionActions;
import app.verimly.task.application.ports.out.security.context.ChangeSessionStatusContext;
import app.verimly.task.domain.repository.TimeSessionWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ChangeSessionStatusAuthorizationRule extends AbstractAuthorizationRule<ChangeSessionStatusContext> {

    public static final Action ACTION = SessionActions.CHANGE_STATUS;
    private final TimeSessionWriteRepository repository;

    @Override
    @Transactional
    public void apply(Principal principal, ChangeSessionStatusContext context) {
        super.ensurePrincipalIsNotNull(principal);
        super.ensureContextIsNotNull(context);

        applyRule(principal, context);
    }

    private void applyRule(Principal principal, ChangeSessionStatusContext context) {
        super.ensurePrincipalIsAuthenticated(principal);

        ensureOwnerOfSession(principal, context);
    }

    private void ensureOwnerOfSession(Principal principal, ChangeSessionStatusContext context) {
        UserId sessionOwnerId = fetchOwnerOfSession(principal, context.getSessionId());
        if (!Objects.equals(principal.getId(), sessionOwnerId)) {
            throw new NoPermissionException(principal, ACTION);
        }
    }

    private UserId fetchOwnerOfSession(Principal principal, SessionId sessionId) {
        return repository.findOwnerOf(sessionId).orElseThrow(() -> new NoPermissionException(principal, ACTION));
    }
}

package app.verimly.user.adapter.security.rule;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.Action;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.user.application.ports.out.security.UserActions;
import app.verimly.user.application.ports.out.security.UserPermissionViolation;
import app.verimly.user.application.ports.out.security.context.ViewUserContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ViewUserAuthorizationRule extends AbstractAuthorizationRule<ViewUserContext> {

    public static final Action ACTION = UserActions.VIEW;

    @Override
    public void apply(Principal principal, ViewUserContext context) {
        super.ensurePrincipalIsNotNull(principal);
        super.ensureContextIsNotNull(context);

        super.ensurePrincipalIsAuthenticated(principal, ACTION);
        ensureHimself(principal, context.getUserId());
    }

    private void ensureHimself(Principal principal, UserId userId) {
        if (!Objects.equals(principal.getId(), userId)) {
            UserPermissionViolation viewUserViolation = UserPermissionViolation.viewUser(principal.getId().getValue(), userId.getValue());
            throw new NoPermissionException(viewUserViolation);
        }
    }
}

package app.verimly.user.adapter.security.rule;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.Action;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.security.Principal;
import app.verimly.user.application.ports.out.security.UserActions;
import app.verimly.user.application.ports.out.security.context.FetchUserDetailsContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FetchUserDetailsAuthorizationRule extends AbstractAuthorizationRule<FetchUserDetailsContext> {

    public static final Action ACTION = UserActions.FETCH_DETAILS;

    @Override
    public void apply(Principal principal, FetchUserDetailsContext context) {
        super.ensurePrincipalIsNotNull(principal);
        super.ensureContextIsNotNull(context);

        super.ensurePrincipalIsAuthenticated(principal, "Principal must be authenticated fetch user details of %s".formatted(context.getUserId()));
        ensureHimself(principal, context.getUserId());
    }

    private void ensureHimself(Principal principal, UserId userId) {
        if (!Objects.equals(principal.getId(), userId))
            throw new NoPermissionException(principal, ACTION);
    }
}

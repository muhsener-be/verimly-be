package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.action.SessionActions;
import app.verimly.task.application.ports.out.security.context.ViewSessionContext;
import org.springframework.stereotype.Component;

@Component
public class ViewSessionAuthorizationRule extends AbstractAuthorizationRule<ViewSessionContext> {

    @Override
    public void apply(Principal principal, ViewSessionContext context) {
        super.ensurePrincipalIsNotNull(principal);
        super.ensureContextIsNotNull(context);

        super.ensurePrincipalIsAuthenticated(principal, SessionActions.VIEW);
    }
}

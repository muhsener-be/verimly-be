package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.AuthorizationContext;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.context.ListFoldersContext;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ListFoldersAuthorizationRule extends AbstractAuthorizationRule<ListFoldersContext> {

    @Override
    public void apply(Principal principal, ListFoldersContext context) {
        super.ensurePrincipalIsNotNull(principal);


        applyRule(principal, context);
    }

    private void applyRule(@NotNull Principal principal, AuthorizationContext resource) {
        super.ensurePrincipalIsAuthenticated(principal, "Principal must be authenticated to list folders");

    }


}

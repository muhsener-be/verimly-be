package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.context.CreateTaskContext;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CreateTaskAuthorizationRule extends AbstractAuthorizationRule<CreateTaskContext> {

    @Override
    public void apply(@NotNull Principal principal, @Nullable CreateTaskContext resource) {
        super.ensurePrincipalIsNotNull(principal);

        applyRule(principal);

    }


    private void applyRule(Principal principal) {
        super.ensurePrincipalIsAuthenticated(principal, "Principal must be authenticated to create task.");
    }


}

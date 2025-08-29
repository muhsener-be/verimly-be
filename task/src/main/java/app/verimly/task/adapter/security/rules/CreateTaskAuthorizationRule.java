package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.*;
import app.verimly.task.application.ports.out.security.context.CreateTaskContext;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CreateTaskAuthorizationRule implements AuthorizationRule<CreateTaskContext> {

    @Override
    public void apply(@NotNull Principal principal, @Nullable CreateTaskContext resource) {
        checkInputs(principal, resource);

        applyRule(principal);

    }

    private void checkInputs(Principal principal, AuthorizationContext resource) {
        Assert.notNull(principal, "Principal cannot be null");


    }


    private void applyRule(Principal principal) {
        ensurePrincipalIsAuthenticated(principal);
    }

    private void ensurePrincipalIsAuthenticated(Principal principal) {
        if (!(principal instanceof AuthenticatedPrincipal auth))
            throw new AuthenticationRequiredException("Authentication required to create task.");
    }


}

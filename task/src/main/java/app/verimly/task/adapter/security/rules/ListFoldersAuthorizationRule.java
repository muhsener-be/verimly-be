package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.*;
import app.verimly.task.application.ports.out.security.context.ListFoldersContext;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ListFoldersAuthorizationRule implements AuthorizationRule<ListFoldersContext> {

    @Override
    public void apply(Principal principal, ListFoldersContext context) {
        Assert.notNull(principal, "Principal cannot be null in %s".formatted(this.getClass().getSimpleName()));


        applyRule(principal, context);
    }

    private void applyRule(@NotNull Principal principal, AuthorizationContext resource) {
        ensurePrincipalIsAuthenticated(principal);

    }

    private static void ensurePrincipalIsAuthenticated(Principal principal) {
        if (principal instanceof AnonymousPrincipal)
            throw new AuthenticationRequiredException("Authentication is required to list folders.");
    }


}

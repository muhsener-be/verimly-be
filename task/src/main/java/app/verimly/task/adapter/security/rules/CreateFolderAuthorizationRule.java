package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.AnonymousPrincipal;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.AuthorizationRule;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.context.CreateFolderContext;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CreateFolderAuthorizationRule implements AuthorizationRule<CreateFolderContext> {


    @Override
    public void apply(@NotNull Principal principal, @Nullable CreateFolderContext context) {
        Assert.notNull(principal, "Principal cannot be null in %s".formatted(this.getClass().getSimpleName()));


        applyRule(principal, context);

    }

    private void applyRule(@NotNull Principal principal, CreateFolderContext resource) {
        ensurePrincipalIsAuthenticated(principal);

    }

    private static void ensurePrincipalIsAuthenticated(Principal principal) {
        if (principal instanceof AnonymousPrincipal)
            throw new AuthenticationRequiredException("Authentication is required to create folder.");
    }


}

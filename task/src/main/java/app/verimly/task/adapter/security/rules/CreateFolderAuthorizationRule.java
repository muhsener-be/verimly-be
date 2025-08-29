package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.security.AbstractAuthorizationRule;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.context.CreateFolderContext;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CreateFolderAuthorizationRule extends AbstractAuthorizationRule<CreateFolderContext> {


    @Override
    public void apply(@NotNull Principal principal, @Nullable CreateFolderContext context) {
        super.ensurePrincipalIsNotNull(principal);
        applyRule(principal, context);

    }

    private void applyRule(@NotNull Principal principal, CreateFolderContext resource) {
        super.ensurePrincipalIsAuthenticated(principal, "Principal must be authenticated to create folder.");
    }


}

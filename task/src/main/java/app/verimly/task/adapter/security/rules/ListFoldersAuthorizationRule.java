package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.*;
import app.verimly.task.application.ports.out.security.action.FolderActions;
import app.verimly.task.application.ports.out.security.resource.FolderResource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ListFoldersAuthorizationRule implements AuthorizationRule {

    @Override
    public void apply(Principal principal, AuthResource resource) {
        Assert.notNull(principal, "Principal cannot be null in %s".formatted(this.getClass().getSimpleName()));

        ensureResourceIsValid(resource);

        applyRule(principal, resource);
    }

    private void applyRule(@NotNull Principal principal, AuthResource resource) {

        if (principal instanceof AnonymousPrincipal)
            throw new AuthenticationRequiredException("Authentication is required to list folders.");


    }

    private void ensureResourceIsValid(AuthResource resource) {
        if (resource != null && !(resource instanceof FolderResource))
            throw new IllegalStateException("AuthResource type must be '%s' in %s".formatted(FolderResource.class, this.getClass().getSimpleName()));
    }

    @Override
    public Action getSupportedAction() {
        return FolderActions.LIST;
    }
}

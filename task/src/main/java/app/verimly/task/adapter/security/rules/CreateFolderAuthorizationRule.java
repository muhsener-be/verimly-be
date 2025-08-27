package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.*;
import app.verimly.task.application.ports.out.security.FolderActions;
import app.verimly.task.application.ports.out.security.FolderResource;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CreateFolderAuthorizationRule implements AuthorizationRule {


    @Override
    public void apply(@NotNull Principal principal, @Nullable AuthResource resource) {
        Assert.notNull(principal, "Principal cannot be null in %s".formatted(this.getClass().getSimpleName()));

        ensureResourceIsValid(resource);

        applyRule(principal, resource);

    }

    private void applyRule(@NotNull Principal principal, AuthResource resource) {

        if (principal instanceof AnonymousPrincipal)
            throw new AuthenticationRequiredException("Authentication is required to create folder.");


    }

    private void ensureResourceIsValid(AuthResource resource) {
        if (resource != null && !(resource instanceof FolderResource))
            throw new IllegalStateException("AuthResource type must be '%s' in %s".formatted(FolderResource.class, this.getClass().getSimpleName()));
    }

    @Override
    public Action getSupportedAction() {
        return FolderActions.CREATE;
    }
}

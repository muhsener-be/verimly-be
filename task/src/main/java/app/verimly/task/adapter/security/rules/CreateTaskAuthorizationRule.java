package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.*;
import app.verimly.task.application.ports.out.security.action.TaskActions;
import app.verimly.task.application.ports.out.security.resource.TaskResource;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CreateTaskAuthorizationRule implements AuthorizationRule {

    @Override
    public void apply(@NotNull Principal principal, @Nullable AuthResource resource) {
        checkInputs(principal, resource);

        applyRule(principal);


    }

    private static void applyRule(Principal principal) {
        if (!(principal instanceof AuthenticatedPrincipal auth))
            throw new AuthenticationRequiredException("Authentication required to create task.");
    }

    private void checkInputs(Principal principal, AuthResource resource) {
        Assert.notNull(principal, "Principal cannot be null");

        if (resource != null) {
            Assert.instanceOf(resource, TaskResource.class, "Resource must be instance of %s".formatted(TaskResource.class.getName()));
        }
    }

    @Override
    public Action getSupportedAction() {
        return TaskActions.CREATE;
    }
}

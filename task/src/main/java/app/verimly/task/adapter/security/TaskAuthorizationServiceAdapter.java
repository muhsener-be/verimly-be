package app.verimly.task.adapter.security;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.*;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskAuthorizationServiceAdapter implements TaskAuthorizationService {

    private final AuthorizationRuleRegistry registry;

    @Override
    public void authorize(@NotNull Principal principal, @NotNull Action action, @Nullable AuthResource resource) throws SecurityException {
        Assert.notNull(principal, "Principal canot be null");
        Assert.notNull(action, "Action cannot be null!");

        AuthorizationRule rule = getRuleOrThrow(action);

        rule.apply(principal, resource);

    }

    private AuthorizationRule getRuleOrThrow(Action action) {
        return Optional.ofNullable(registry.get(action)).orElseThrow(() -> new IllegalStateException("Authorization rule could not for action: " + action));
    }
}

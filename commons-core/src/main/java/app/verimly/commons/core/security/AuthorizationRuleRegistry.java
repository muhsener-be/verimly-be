package app.verimly.commons.core.security;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthorizationRuleRegistry {

    private final Map<Action, AuthorizationRule> context = new HashMap<>();

    public AuthorizationRuleRegistry(List<AuthorizationRule> authorizationRules) {
        authorizationRules.forEach(authorizationRule -> {
            context.put(authorizationRule.getSupportedAction(), authorizationRule);
        });
    }


    public AuthorizationRule get(Action action) {
        return context.get(action);
    }
}

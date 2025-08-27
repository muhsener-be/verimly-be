package app.verimly.commons.core.security;

public interface AuthorizationRule {

    void apply(Principal principal, AuthResource resource);

    Action getSupportedAction();
}

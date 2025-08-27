package app.verimly.commons.core.security;

public interface AuthorizationService {

    void authorize(Principal principal, Action action, AuthResource resource) throws SecurityException;
}

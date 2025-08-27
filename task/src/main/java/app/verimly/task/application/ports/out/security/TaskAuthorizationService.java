package app.verimly.task.application.ports.out.security;

import app.verimly.commons.core.security.*;
import app.verimly.commons.core.security.SecurityException;

public interface TaskAuthorizationService extends AuthorizationService {

    @Override
    void authorize(Principal principal, Action action, AuthResource resource) throws SecurityException;
}

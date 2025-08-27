package app.verimly.task.application.ports.out.security;

import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;

public interface TaskAuthenticationService extends AuthenticationService {

    Principal getCurrentPrincipal();
}

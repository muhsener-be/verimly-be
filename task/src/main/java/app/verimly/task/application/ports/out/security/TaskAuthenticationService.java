package app.verimly.task.application.ports.out.security;

import app.verimly.commons.core.security.Principal;

public interface TaskAuthenticationService {

    Principal getCurrentPrincipal();
}

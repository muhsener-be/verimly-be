package app.verimly.task.application.usecase.query.task;

import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class AbstractTaskQueryHandler {

    private final AuthenticationService authN;


    protected Principal getCurrentPrincipal() {
        return authN.getCurrentPrincipal();
    }
}

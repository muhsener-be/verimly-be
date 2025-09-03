package app.verimly.user.application.ports.out.security;

import app.verimly.commons.core.security.AuthorizationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.user.application.ports.out.security.context.ViewUserContext;

public interface UserAuthorizationService extends AuthorizationService {

    void authorizeFetchDetails(Principal principal, ViewUserContext context);
}

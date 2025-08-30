package app.verimly.user.application.ports.out.security;

import app.verimly.commons.core.security.AuthorizationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.user.application.ports.out.security.context.FetchUserDetailsContext;

public interface UserAuthorizationService extends AuthorizationService {

    void authorizeFetchDetails(Principal principal, FetchUserDetailsContext context);
}

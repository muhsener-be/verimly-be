package app.verimly.user.application.ports.out.security;

import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.user.application.ports.out.security.context.FetchUserDetailsContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSecurityGateway {
    private final AuthenticationService authN;
    private final UserAuthorizationService authZ;


    public Principal getCurrentPrincipal() {
        return authN.getCurrentPrincipal();
    }

    public void authorizeFetchUserDetails(Principal principal, FetchUserDetailsContext context) {
        authZ.authorizeFetchDetails(principal, context);
    }

}

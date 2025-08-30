package app.verimly.user.adapter.security;

import app.verimly.commons.core.security.Principal;
import app.verimly.user.adapter.security.rule.FetchUserDetailsAuthorizationRule;
import app.verimly.user.application.ports.out.security.UserAuthorizationService;
import app.verimly.user.application.ports.out.security.context.FetchUserDetailsContext;
import org.springframework.stereotype.Component;

@Component
public class UserAuthorizationServiceAdapter implements UserAuthorizationService {

    private final FetchUserDetailsAuthorizationRule fetchUserDetailsAuthorizationRule;

    public UserAuthorizationServiceAdapter(FetchUserDetailsAuthorizationRule fetchUserDetailsAuthorizationRule) {
        this.fetchUserDetailsAuthorizationRule = fetchUserDetailsAuthorizationRule;
    }

    @Override
    public void authorizeFetchDetails(Principal principal, FetchUserDetailsContext context) {
        fetchUserDetailsAuthorizationRule.apply(principal, context);
    }
}

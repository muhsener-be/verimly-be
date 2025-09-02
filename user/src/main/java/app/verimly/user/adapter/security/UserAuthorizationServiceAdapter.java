package app.verimly.user.adapter.security;

import app.verimly.commons.core.security.Principal;
import app.verimly.user.adapter.security.rule.ViewUserAuthorizationRule;
import app.verimly.user.application.ports.out.security.UserAuthorizationService;
import app.verimly.user.application.ports.out.security.context.ViewUserContext;
import org.springframework.stereotype.Component;

@Component
public class UserAuthorizationServiceAdapter implements UserAuthorizationService {

    private final ViewUserAuthorizationRule viewUserAuthorizationRule;

    public UserAuthorizationServiceAdapter(ViewUserAuthorizationRule viewUserAuthorizationRule) {
        this.viewUserAuthorizationRule = viewUserAuthorizationRule;
    }

    @Override
    public void authorizeFetchDetails(Principal principal, ViewUserContext context) {
        viewUserAuthorizationRule.apply(principal, context);
    }
}

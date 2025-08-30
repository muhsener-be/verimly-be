package app.verimly.commons.core.adapter.security;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TaskAuthenticationServiceAdapter implements AuthenticationService {


    @Override
    public Principal getCurrentPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated())
            return new AnonymousPrincipal();

        if (authentication instanceof UsernamePasswordAuthenticationToken auth) {
            SecurityUser user = (SecurityUser) auth.getPrincipal();
            return AuthenticatedPrincipal.of(UserId.of(user.getId()), Email.of(user.getUsername()));
        }

        if (authentication instanceof AnonymousAuthenticationToken)
            return new AnonymousPrincipal();

        throw new IllegalStateException("Authentication token must be instance of UsernamePasswordAuthenticationToken or AnonymousAuthenticationToken." +
                " But found: " + authentication.getClass());
    }
}

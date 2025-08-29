package app.verimly.commons.core.security;

import app.verimly.commons.core.domain.exception.Assert;

public abstract class AbstractAuthorizationRule<T extends AuthorizationContext> implements AuthorizationRule<T> {


    protected void ensurePrincipalIsNotNull(Principal principal, String errorMessage) {
        Assert.notNull(principal, errorMessage);
    }

    protected void ensureContextIsNotNull(AuthorizationContext context, String errorMessage) {
        Assert.notNull(context, errorMessage);
    }

    protected void ensurePrincipalIsAuthenticated(Principal principal, String errorMessage) throws AuthenticationRequiredException {
        if (!(principal instanceof AuthenticatedPrincipal))
            throw new AuthenticationRequiredException(errorMessage);
    }
}

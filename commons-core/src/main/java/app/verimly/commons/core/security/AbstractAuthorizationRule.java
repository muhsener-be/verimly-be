package app.verimly.commons.core.security;

import app.verimly.commons.core.domain.exception.Assert;

public abstract class AbstractAuthorizationRule<T extends AuthorizationContext> implements AuthorizationRule<T> {


    protected void ensurePrincipalIsNotNull(Principal principal) {
        Assert.notNull(principal, "Principal cannot be null to apply %s".formatted(getClass().getSimpleName()));
    }

    protected void ensureContextIsNotNull(AuthorizationContext context) {
        Assert.notNull(context, "Context cannot be null to apply %s".formatted(getClass().getSimpleName()));
    }

    protected void ensurePrincipalIsAuthenticated(Principal principal, String errorMessage) throws AuthenticationRequiredException {
        if (!(principal instanceof AuthenticatedPrincipal))
            throw new AuthenticationRequiredException(errorMessage);
    }

    protected void ensurePrincipalIsAuthenticated(Principal principal) {
        ensurePrincipalIsAuthenticated(principal, "Principal [ID: %s] must be authenticated to apply %s".formatted(principal.getId(), getClass().getSimpleName()));
    }


}

package app.verimly.commons.core.security;

public interface AuthorizationRule<T extends AuthorizationContext> {

    void apply(Principal principal, T context);


}

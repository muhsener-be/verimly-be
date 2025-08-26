package app.verimly.commons.core.security;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import lombok.Getter;

@Getter
public class AuthenticatedPrincipal implements Principal {

    private final UserId id;
    private final Email email;


    protected AuthenticatedPrincipal(UserId id, Email email) {
        this.id = id;
        this.email = email;
    }

    public static AuthenticatedPrincipal of(UserId userId, Email email) {
        AuthenticatedPrincipal authenticatedPrincipal = new AuthenticatedPrincipal(userId, email);
        authenticatedPrincipal.checkInvariants();
        return authenticatedPrincipal;

    }


    protected void checkInvariants() {
        if (id == null)
            throw new InvalidDomainObjectException(Errors.ID_NOT_EXIST);

        if (email == null)
            throw new InvalidDomainObjectException(Errors.EMAIL_NOT_EXIST);
    }


    @Override
    public UserId getId() {
        return id;
    }

    @Override
    public Email getEmail() {
        return email;
    }


    public static final class Errors {
        public static final ErrorMessage ID_NOT_EXIST = ErrorMessage.of("principal.id-not-exist", "Authenticated principal must have an ID");
        public static final ErrorMessage EMAIL_NOT_EXIST = ErrorMessage.of("principal.email-not-exist", "Authenticated principal must have an email");

    }
}

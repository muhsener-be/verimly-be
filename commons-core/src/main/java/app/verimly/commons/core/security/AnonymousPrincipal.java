package app.verimly.commons.core.security;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;

public class AnonymousPrincipal implements Principal {

    @Override
    public UserId getId() {
        return null;
    }

    @Override
    public Email getEmail() {
        return null;
    }
}

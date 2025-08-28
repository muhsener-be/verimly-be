package app.verimly.task.data;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AnonymousPrincipal;
import app.verimly.commons.core.security.AuthenticatedPrincipal;
import app.verimly.commons.core.security.Principal;

public class PrincipalTestData {

    private static final PrincipalTestData INSTANCE = new PrincipalTestData();

    public static PrincipalTestData getInstance() {
        return INSTANCE;
    }

    public Principal authenticatedPrincipal() {
        return AuthenticatedPrincipal.of(UserId.random(), Email.of("random@email.com"));
    }

    public Principal anonymousPrincipal() {
        return new AnonymousPrincipal();
    }
}

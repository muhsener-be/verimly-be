package app.verimly.commons.core.security;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.UserId;

public class NoPermissionException extends SecurityException {

    public static final ErrorMessage errorMessage = ErrorMessage.of("forbidden", "Forbidden");


    public NoPermissionException(Principal principal, Action action) {
        super(errorMessage, prepareMessage(principal.getId(), action));
    }

    public NoPermissionException(UserId principalId, Action action) {
        super(errorMessage, prepareMessage(principalId, action));
    }

    private static String prepareMessage(UserId id, Action action) {
        return "User with ID '%s' has no permission to %s".formatted(id == null ? null : id.getValue(), action);
    }
}

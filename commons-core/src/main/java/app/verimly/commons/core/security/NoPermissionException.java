package app.verimly.commons.core.security;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.UserId;
import lombok.Getter;

public class NoPermissionException extends SecurityException {

    @Getter
    private PermissionViolation violation;


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

    public NoPermissionException(PermissionViolation violation) {
        super("Principal %s has no permission to perform %s on %s (required: %s)"
                .formatted(
                        violation.getPrincipal(),
                        violation.getAction(),
                        violation.getResource(),
                        violation.getRequirement().getExpression()
                ));
        this.violation = violation;
    }

}

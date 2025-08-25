package app.verimly.user.application.exception;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.Email;
import lombok.Getter;

public class DuplicateEmailException extends UserBusinessException {

    private static final ErrorMessage ERROR_MESSAGE = ErrorMessage.of("email.duplicated", "Email is already taken.");
    public static final String MESSAGE_TEMPLATE = "Provided email '%s' is already taken.";

    @Getter
    private final Email email;

    public DuplicateEmailException(Email email) {
        super(ERROR_MESSAGE, checkEmailInputAndPrepareMessage(email));
        this.email = email;
    }

    private static String checkEmailInputAndPrepareMessage(Email email) {
        Assert.notNull(email, "Email cannot be null.");
        String value = email.getValue();
        return MESSAGE_TEMPLATE.formatted(value);
    }
}

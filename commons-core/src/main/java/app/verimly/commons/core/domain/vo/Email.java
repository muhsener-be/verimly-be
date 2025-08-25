package app.verimly.commons.core.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.validation.InputValidator;

public class Email extends ValueObject<String> {


    protected Email(String value) {
        super(value);
    }

    public static Email of(String value) {
        if (value == null) {
            return null;
        }
        return new Email(validateAndFormat(value));
    }

    private static String validateAndFormat(String value) {
        assert value != null;

        String trimmed = value.trim();
        if(!InputValidator.isEmailValid(trimmed))
            throw new InvalidDomainObjectException(Errors.FORMAT);

        return trimmed;
    }

    public static Email reconstruct(String value) {
        return new Email(value);
    }


    public static final class Errors {
        public static final ErrorMessage FORMAT = ErrorMessage.of("email.format", "Invalid email format.");
    }

}

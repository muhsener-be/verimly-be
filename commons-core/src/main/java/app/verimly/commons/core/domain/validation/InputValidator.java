package app.verimly.commons.core.domain.validation;

import org.apache.commons.validator.routines.EmailValidator;

public class InputValidator {
    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();

    public static boolean isEmailValid(String email) {
        if (email == null || email.isBlank())
            return false;


        return EMAIL_VALIDATOR.isValid(email);
    }
}

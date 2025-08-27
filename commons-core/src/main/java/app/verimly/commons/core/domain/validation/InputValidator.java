package app.verimly.commons.core.domain.validation;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;

/**
 * Utility class for input validation in the domain layer.
 * <p>
 * Provides static methods to validate user input, such as email addresses,
 * using the Apache Commons Validator library.
 * </p>
 */
public class InputValidator {
    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
    private static final Pattern HEX_COLOR_PATTERN =
            Pattern.compile("^#(?:[0-9a-fA-F]{3}|[0-9a-fA-F]{6})$");

    /**
     * Validates whether the given string is a valid email address.
     *
     * @param email the email address to validate
     * @return {@code true} if the email is valid, {@code false} otherwise
     */
    public static boolean isEmailValid(String email) {
        if (email == null || email.isBlank())
            return false;


        return EMAIL_VALIDATOR.isValid(email);
    }

    public static boolean isColorValid(String hexFormat) {
        if (hexFormat == null)
            return false;

        return HEX_COLOR_PATTERN.matcher(hexFormat).matches();


    }
}

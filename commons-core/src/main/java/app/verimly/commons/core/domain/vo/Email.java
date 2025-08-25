package app.verimly.commons.core.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.validation.InputValidator;

/**
 * Value object representing an email address.
 * <p>
 * Provides validation and formatting for email addresses using the Apache Commons Validator library.
 * Use {@link #of(String)} for creation with validation, or {@link #reconstruct(String)} for mapping from persistence without validation.
 * </p>
 */
public class Email extends ValueObject<String> {

    /**
     * Protected constructor for Email value object.
     *
     * @param value the email address string
     */
    protected Email(String value) {
        super(value);
    }

    /**
     * Creates a new {@code Email} value object after validating and formatting the input.
     * Returns {@code null} if the input value is {@code null}.
     *
     * @param value the email address string
     * @return a validated {@code Email} object or {@code null}
     * @throws InvalidDomainObjectException if the email format is invalid
     */
    public static Email of(String value) {
        if (value == null) {
            return null;
        }
        String formattedEmail = validateAndFormat(value);
        return new Email(formattedEmail);
    }

    /**
     * Validates and formats the given email address string.
     * Trims the input and checks format using {@link InputValidator}.
     *
     * @param value the email address string
     * @return the trimmed email address string
     * @throws InvalidDomainObjectException if the email format is invalid
     */
    private static String validateAndFormat(String value) {
        assert value != null;

        String trimmed = value.trim();
        if (!InputValidator.isEmailValid(trimmed))
            throw new InvalidDomainObjectException(Errors.FORMAT);

        return trimmed;
    }

    /**
     * Reconstructs an {@code Email} value object from a raw value without validation.
     * <p>
     * This method should only be used when mapping from a persistence entity.
     * It does not check invariants.
     * </p>
     *
     * @param value the email address string
     * @return a new {@code Email} object
     */
    public static Email reconstruct(String value) {
        return new Email(value);
    }

    /**
     * Error messages for the {@code Email} value object.
     */
    public static final class Errors {

        public static final ErrorMessage FORMAT = ErrorMessage.of("email.format", "Invalid email format.");
    }

}

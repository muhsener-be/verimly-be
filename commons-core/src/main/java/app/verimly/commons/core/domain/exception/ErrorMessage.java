package app.verimly.commons.core.domain.exception;

import org.springframework.web.ErrorResponse;

/**
 * Represents an error message with a code and a default message.
 * <p>
 * Used for conveying error information in domain exceptions and other error-handling scenarios.
 * </p>
 *
 * @param code           the unique error code (must not be null)
 * @param defaultMessage the default error message (never null, defaults to empty string if null)
 */
public record ErrorMessage(String code, String defaultMessage) {

    /**
     * Constructs an {@code ErrorMessage} record.
     * Ensures that {@code code} is not null and {@code defaultMessage} is never null.
     *
     * @param code           the error code
     * @param defaultMessage the default error message
     * @throws IllegalArgumentException if {@code code} is null
     */
    public ErrorMessage {
        Assert.notNull(code, "ErrorMessage code cannot be null!");
        if (defaultMessage == null)
            defaultMessage = "";
    }

    /**
     * Creates a new {@code ErrorMessage} instance.
     *
     * @param code           the error code
     * @param defaultMessage the default error message
     * @return a new {@code ErrorMessage}
     */
    public static ErrorMessage of(String code, String defaultMessage) {
        return new ErrorMessage(code, defaultMessage);
    }

    /**
     * Returns a generic unknown error message.
     *
     * @return an {@code ErrorMessage} representing an unknown error
     */
    public static ErrorMessage unknown() {
        return new ErrorMessage("unknown", "unknown error.");
    }



    public ErrorMessage withDefaultMessage(String defaultMessage) {
        return of(this.code, defaultMessage);

    }
}

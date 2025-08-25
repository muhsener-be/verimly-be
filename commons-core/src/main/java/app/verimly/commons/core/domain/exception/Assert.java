package app.verimly.commons.core.domain.exception;

/**
 * Utility class for assertion checks in the domain layer.
 * <p>
 * Provides static methods to validate method arguments and state,
 * throwing {@link IllegalArgumentException} if the check fails.
 * </p>
 */
public class Assert {

    /**
     * Asserts that the provided object is not {@code null}.
     *
     * @param object  the object to check
     * @param message the exception message to use if the check fails
     * @param <T>     the type of the object
     * @return the validated object (never {@code null})
     * @throws IllegalArgumentException if {@code object} is {@code null}
     */
    public static <T> T notNull(T object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
        return object;
    }

    /**
     * Asserts that the provided string is not {@code null} and not blank.
     *
     * @param value   the string to check
     * @param message the exception message to use if the check fails
     * @return the validated string (never {@code null} or blank)
     * @throws IllegalArgumentException if {@code value} is {@code null} or blank
     */
    public static String notBlank(String value, String message) {
        if (notNull(value, message).isBlank())
            throw new IllegalArgumentException(message);
        return value;
    }

    public static int notNegative(int value, String message) {
        if (value < 0)
            throw new IllegalArgumentException(message);

        return value;

    }
}

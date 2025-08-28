package app.verimly.commons.core.domain.exception;

import java.util.Objects;

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

    public static void equals(Object o1, Object o2, String message) {
        if (!Objects.equals(o1, o2))
            throw new IllegalArgumentException(message);
    }

    /**
     * Asserts that the provided object is an instance of the specified class.
     *
     * @param <T>     the expected type
     * @param object  the object to check
     * @param clazz   the class to check against
     * @param message the exception message to use if the check fails
     * @return the object cast to the specified type
     * @throws IllegalArgumentException if {@code object} is not an instance of {@code clazz}
     * @throws IllegalArgumentException if {@code clazz} is {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Object object, Class<T> clazz, String message) {
        notNull(clazz, "Target class must not be null");
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(message);
        }
        return (T) object;
    }
}

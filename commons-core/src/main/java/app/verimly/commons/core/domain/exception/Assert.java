package app.verimly.commons.core.domain.exception;

public class Assert {

    public static <T> T notNull(T object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
        return object;
    }

    public static String notBlank(String value, String message) {
        if (notNull(value, message).isBlank())
            throw new IllegalArgumentException(message);
        return value;
    }
}

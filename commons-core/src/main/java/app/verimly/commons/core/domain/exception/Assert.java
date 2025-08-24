package app.verimly.commons.core.domain.exception;

public class Assert {

    public static <T> T notNull(T object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
        return object;
    }
}

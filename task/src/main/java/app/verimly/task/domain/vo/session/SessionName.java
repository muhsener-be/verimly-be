package app.verimly.task.domain.vo.session;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.vo.ValueObject;

public class SessionName extends ValueObject<String> {

    public static final int MAX_LENGTH = 30;

    protected SessionName(String value) {
        super(value);
    }

    public static SessionName of(String value) {
        if (value == null || value.isBlank())
            return null;

        String normalizedValue = normalized(value);
        SessionName name = new SessionName(normalizedValue);
        name.checkInvariants();
        return name;
    }

    public static SessionName reconstruct(String value) {
        return value == null ? null : new SessionName(value);
    }


    private void checkInvariants() {
        if (value.length() > MAX_LENGTH)
            throw new InvalidDomainObjectException(Errors.LENGTH);
    }


    private static String normalized(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }


    public static final class Errors {
        public static final ErrorMessage LENGTH = ErrorMessage.of("session-name.length", "Session name must be between 1 and %s characters.".formatted(MAX_LENGTH));
    }
}

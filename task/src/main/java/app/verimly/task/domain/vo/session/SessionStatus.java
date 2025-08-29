package app.verimly.task.domain.vo.session;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;

import java.util.Locale;

public enum SessionStatus {
    RUNNING, PAUSED, FINISHED;

    public static SessionStatus of(String value) {
        if (value == null || value.isBlank())
            return null;

        String upperCase = value.toUpperCase(Locale.US);
        return switch (upperCase) {
            case "RUNNING" -> RUNNING;
            case "PAUSED" -> PAUSED;
            case "FINISHED" -> FINISHED;
            default -> throw new InvalidDomainObjectException("Unknown session status type: " + value);
        };
    }
}

package app.verimly.task.adapter.web.dto.common;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;

import java.util.Locale;

public enum SessionStatusAction {
    PAUSE, RESUME, FINISH;

    public static SessionStatusAction of(String value) {
        if (null == value || value.isBlank())
            return null;

        String upperCase = value.toUpperCase(Locale.US);
        return switch (upperCase) {
            case "PAUSE" -> PAUSE;
            case "RESUME" -> RESUME;
            case "FINISH" -> FINISH;
            default -> throw new InvalidDomainObjectException("Unknown session status action: " + value);
        };

    }
}

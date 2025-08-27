package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;

import java.util.Locale;

public enum Priority {

    LOW, MEDIUM, HIGH;

    public static Priority of(String value) {
        if (value == null || value.isBlank())
            return null;

        String upperCase = value.trim().toUpperCase(Locale.US);
        return switch (upperCase) {
            case "LOW" -> LOW;
            case "MEDIUM" -> MEDIUM;
            case "HIGH" -> HIGH;
            default -> throw new InvalidDomainObjectException("Unknown priority type: " + value);
        };
    }
}

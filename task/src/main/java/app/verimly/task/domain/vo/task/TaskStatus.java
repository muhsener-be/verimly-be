package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;

import java.util.Locale;

public enum TaskStatus {

    NOT_STARTED, IN_PROGRESS, DONE;

    public static TaskStatus of(String value) {
        if (value == null || value.isBlank())
            return null;

        String lower = value.toLowerCase(Locale.US);
        return switch (lower) {
            case "not_started" -> NOT_STARTED;
            case "in_progress" -> IN_PROGRESS;
            case "done" -> DONE;
            default -> throw new InvalidDomainObjectException("Unknown status: " + value);
        };

    }
}

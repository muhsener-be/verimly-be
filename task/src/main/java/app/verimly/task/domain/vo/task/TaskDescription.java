package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.vo.ValueObject;

public class TaskDescription extends ValueObject<String> {


    public static final int MAX_LENGTH = 240;

    protected TaskDescription(String value) {
        super(value);
    }

    public static TaskDescription of(String value) {
        if (value == null || value.isBlank())
            return null;

        String normalizedValue = normalize(value);
        TaskDescription taskDescription = new TaskDescription(normalizedValue);
        taskDescription.checkInvariants();
        return taskDescription;
    }

    public static TaskDescription reconstruct(String description) {
        return description == null ? null : new TaskDescription(description);
    }

    private void checkInvariants() {
        if (value.length() > MAX_LENGTH)
            throw new InvalidDomainObjectException(Errors.LENGTH);
    }

    private static String normalize(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }

    public static final class Errors {
        public static final ErrorMessage LENGTH = ErrorMessage.of("task-description.length", "Description of the task must be at most %d long.".formatted(MAX_LENGTH));
    }
}

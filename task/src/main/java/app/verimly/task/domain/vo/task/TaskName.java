package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.vo.ValueObject;

public class TaskName extends ValueObject<String> {

    public static final int MAX_LENGTH = 50;

    protected TaskName(String value) {
        super(value);
    }


    public static TaskName of(String value) {
        if (value == null || value.isBlank())
            return null;

        String normalizedName = normalize(value);
        TaskName name = new TaskName(normalizedName);
        name.checkInvariants();
        return name;
    }

    public static TaskName reconstruct(String name) {
        return name == null ? null : new TaskName(name);
    }

    private void checkInvariants() {
        if (value.length() > MAX_LENGTH)
            throw new InvalidDomainObjectException(Errors.LENGTH);

    }

    private static String normalize(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }


    public static final class Errors {
        public static final ErrorMessage LENGTH = ErrorMessage.of("task-name.length", "Name of the task must be at most %d long.".formatted(MAX_LENGTH));
    }
}

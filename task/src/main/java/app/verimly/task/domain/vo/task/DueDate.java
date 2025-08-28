package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.vo.ValueObject;

import java.time.Instant;


public class DueDate extends ValueObject<Instant> {


    protected DueDate(Instant value) {
        super(value);
    }


    public static DueDate of(Instant value) {
        if (value == null)
            return null;

        DueDate dueDate = new DueDate(value);
        dueDate.checkInvariants();
        return dueDate;

    }

    private void checkInvariants() {
        if (value.isBefore(Instant.now())) {
            throw new InvalidDomainObjectException(Errors.PAST);
        }
    }


    public static DueDate reconstruct(Instant instant) {
        return instant == null ? null : new DueDate(instant);
    }

    public static final class Errors {
        public static final ErrorMessage PAST = ErrorMessage.of("due-date.past", "Due date cannot be in the past.");
    }
}

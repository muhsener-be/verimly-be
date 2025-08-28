package app.verimly.task.domain.exception;

import app.verimly.commons.core.domain.exception.DomainException;
import app.verimly.commons.core.domain.exception.ErrorMessage;

public class TaskDomainException extends DomainException {

    public TaskDomainException() {
    }

    public TaskDomainException(String message) {
        super(message);
    }

    public TaskDomainException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public TaskDomainException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public TaskDomainException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

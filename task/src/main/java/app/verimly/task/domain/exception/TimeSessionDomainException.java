package app.verimly.task.domain.exception;

import app.verimly.commons.core.domain.exception.DomainException;
import app.verimly.commons.core.domain.exception.ErrorMessage;

public class TimeSessionDomainException extends DomainException {

    public TimeSessionDomainException() {
    }

    public TimeSessionDomainException(String message) {
        super(message);
    }

    public TimeSessionDomainException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public TimeSessionDomainException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public TimeSessionDomainException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

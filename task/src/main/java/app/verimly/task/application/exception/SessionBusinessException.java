package app.verimly.task.application.exception;

import app.verimly.commons.core.domain.exception.BusinessException;
import app.verimly.commons.core.domain.exception.ErrorMessage;

public class SessionBusinessException extends BusinessException {


    public SessionBusinessException() {
    }

    public SessionBusinessException(String message) {
        super(message);
    }

    public SessionBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionBusinessException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public SessionBusinessException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public SessionBusinessException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public SessionBusinessException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

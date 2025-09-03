package app.verimly.task.application.exception;

import app.verimly.commons.core.domain.exception.BusinessException;
import app.verimly.commons.core.domain.exception.ErrorMessage;

public class TaskBusinessException extends BusinessException {

    public TaskBusinessException() {
    }

    public TaskBusinessException(String message) {
        super(message);
    }

    public TaskBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskBusinessException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public TaskBusinessException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public TaskBusinessException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public TaskBusinessException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

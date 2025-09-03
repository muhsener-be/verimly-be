package app.verimly.task.application.exception;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.SystemException;

public class TaskSystemException extends SystemException {
    public TaskSystemException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }

    public TaskSystemException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public TaskSystemException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public TaskSystemException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public TaskSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskSystemException(String message) {
        super(message);
    }

    public TaskSystemException() {
    }
}

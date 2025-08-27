package app.verimly.task.domain.repository;

import app.verimly.commons.core.domain.exception.DataAccessException;
import app.verimly.commons.core.domain.exception.ErrorMessage;

public class TaskDataAccessException extends DataAccessException {


    public TaskDataAccessException() {
    }

    public TaskDataAccessException(String message) {
        super(message);
    }

    public TaskDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskDataAccessException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public TaskDataAccessException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public TaskDataAccessException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public TaskDataAccessException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

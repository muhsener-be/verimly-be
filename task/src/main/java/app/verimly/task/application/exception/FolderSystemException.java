package app.verimly.task.application.exception;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.SystemException;

public class FolderSystemException extends SystemException {


    public FolderSystemException() {
    }

    public FolderSystemException(String message) {
        super(message);
    }

    public FolderSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderSystemException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public FolderSystemException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public FolderSystemException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public FolderSystemException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

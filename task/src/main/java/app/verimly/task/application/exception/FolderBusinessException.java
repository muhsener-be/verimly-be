package app.verimly.task.application.exception;

import app.verimly.commons.core.domain.exception.BusinessException;
import app.verimly.commons.core.domain.exception.ErrorMessage;

public class FolderBusinessException extends BusinessException {

    public FolderBusinessException() {
    }

    public FolderBusinessException(String message) {
        super(message);
    }

    public FolderBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderBusinessException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public FolderBusinessException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public FolderBusinessException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public FolderBusinessException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

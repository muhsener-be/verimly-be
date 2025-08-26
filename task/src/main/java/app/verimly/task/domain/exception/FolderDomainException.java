package app.verimly.task.domain.exception;

import app.verimly.commons.core.domain.exception.DomainException;
import app.verimly.commons.core.domain.exception.ErrorMessage;

public class FolderDomainException extends DomainException {


    public FolderDomainException() {
        super();
    }

    public FolderDomainException(String message) {
        super(message);
    }

    public FolderDomainException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public FolderDomainException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public FolderDomainException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

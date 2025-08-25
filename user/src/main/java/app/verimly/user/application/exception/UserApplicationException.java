package app.verimly.user.application.exception;

import app.verimly.commons.core.domain.exception.ApplicationException;
import app.verimly.commons.core.domain.exception.ErrorMessage;

public class UserApplicationException extends ApplicationException {

    public UserApplicationException() {
    }

    public UserApplicationException(String message) {
        super(message);
    }

    public UserApplicationException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public UserApplicationException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public UserApplicationException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public UserApplicationException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

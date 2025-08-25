package app.verimly.user.application.exception;

import app.verimly.commons.core.domain.exception.ErrorMessage;

public class UserSystemException extends UserApplicationException {

    public UserSystemException() {
    }

    public UserSystemException(String message) {
        super(message);
    }

    public UserSystemException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public UserSystemException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public UserSystemException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public UserSystemException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

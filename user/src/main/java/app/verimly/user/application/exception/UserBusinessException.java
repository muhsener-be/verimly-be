package app.verimly.user.application.exception;

import app.verimly.commons.core.domain.exception.ErrorMessage;

public class UserBusinessException extends UserApplicationException {


    public UserBusinessException() {
    }

    public UserBusinessException(String message) {
        super(message);
    }

    public UserBusinessException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public UserBusinessException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public UserBusinessException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public UserBusinessException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

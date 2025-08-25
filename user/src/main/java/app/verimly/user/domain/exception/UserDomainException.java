package app.verimly.user.domain.exception;

import app.verimly.commons.core.domain.exception.DomainException;
import app.verimly.commons.core.domain.exception.ErrorMessage;

public class UserDomainException extends DomainException {

    public UserDomainException() {
    }

    public UserDomainException(String message) {
        super(message);
    }

    public UserDomainException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public UserDomainException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public UserDomainException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

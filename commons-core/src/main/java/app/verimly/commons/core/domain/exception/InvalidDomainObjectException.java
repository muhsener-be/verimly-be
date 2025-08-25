package app.verimly.commons.core.domain.exception;

public class InvalidDomainObjectException extends DomainException {


    protected InvalidDomainObjectException() {
        super();
    }

    public InvalidDomainObjectException(String message) {
        super(message);
    }

    public InvalidDomainObjectException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public InvalidDomainObjectException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public InvalidDomainObjectException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

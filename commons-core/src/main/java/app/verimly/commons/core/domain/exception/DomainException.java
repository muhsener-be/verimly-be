package app.verimly.commons.core.domain.exception;

public abstract class DomainException extends AbstractException {


    public DomainException() {
        super();
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public DomainException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public DomainException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

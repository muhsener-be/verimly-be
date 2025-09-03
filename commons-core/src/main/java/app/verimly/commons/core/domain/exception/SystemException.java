package app.verimly.commons.core.domain.exception;

public abstract class SystemException extends ApplicationException {

    public SystemException() {
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public SystemException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public SystemException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public SystemException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

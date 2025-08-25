package app.verimly.commons.core.domain.exception;

public abstract class SecurityException extends ApplicationException {


    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(ErrorMessage errorMessage, String message) {
        super(errorMessage,message);
    }


    public SecurityException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

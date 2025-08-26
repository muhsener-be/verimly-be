package app.verimly.commons.core.domain.exception;

public abstract class ApplicationException extends AbstractException {


    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }


    public ApplicationException(ErrorMessage errorMessage) {
        super(errorMessage);
    }


    public ApplicationException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }


    public ApplicationException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public ApplicationException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

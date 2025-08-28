package app.verimly.commons.core.domain.exception;

public class ConflictException extends BusinessException{

    public ConflictException() {
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public ConflictException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public ConflictException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public ConflictException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

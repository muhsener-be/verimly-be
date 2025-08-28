package app.verimly.commons.core.domain.exception;

public class BusinessException extends ApplicationException {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public BusinessException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public BusinessException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public BusinessException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

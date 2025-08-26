package app.verimly.commons.core.domain.exception;

public abstract class DataAccessException extends ApplicationException {


    public DataAccessException() {
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public DataAccessException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public DataAccessException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }


    public DataAccessException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}

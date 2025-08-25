package app.verimly.commons.core.domain.exception;

public abstract class DataAccessException extends ApplicationException {


    public DataAccessException(String message) {
        super(message);
    }
}

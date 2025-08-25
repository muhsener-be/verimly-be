package app.verimly.commons.core.domain.exception;

public abstract class ApplicationException extends RuntimeException {


    public ApplicationException(String message) {
        super(message);
    }
}

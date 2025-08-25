package app.verimly.commons.core.domain.exception;

import lombok.Getter;

@Getter
public abstract class DomainException extends RuntimeException {
    private final ErrorMessage errorMessage;


    public DomainException() {
        this(ErrorMessage.unknown(),ErrorMessage.unknown().defaultMessage() , null);
    }

    public DomainException(String message) {
        this(ErrorMessage.unknown(), message, null);
    }

    public DomainException(ErrorMessage errorMessage) {
        this(errorMessage, controlErrorMessage(errorMessage).defaultMessage(), null);
    }

    public DomainException(ErrorMessage errorMessage, String message) {
        this(errorMessage, message, null);
    }

    public DomainException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = controlErrorMessage(errorMessage);
    }

    private static ErrorMessage controlErrorMessage(ErrorMessage errorMessage) {
        if (errorMessage == null)
            return ErrorMessage.unknown();

        return errorMessage;
    }

}

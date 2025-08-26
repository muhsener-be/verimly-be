package app.verimly.commons.core.domain.exception;

import lombok.Getter;

public abstract class AbstractException extends RuntimeException {


    private final static ErrorMessage UNKNOWN_ERROR_MESSAGE = ErrorMessage.unknown();

    @Getter
    private final ErrorMessage errorMessage;


    public AbstractException() {
        this(UNKNOWN_ERROR_MESSAGE, UNKNOWN_ERROR_MESSAGE.defaultMessage(), null, 0);
    }


    public AbstractException(String message) {
        this(UNKNOWN_ERROR_MESSAGE, message, null, 0);
    }

    public AbstractException(String message, Throwable cause) {
        this(UNKNOWN_ERROR_MESSAGE, message, cause, 0);
    }


    public AbstractException(ErrorMessage errorMessage) {
        this(ensureErrorMessageNotNull(errorMessage), errorMessage.defaultMessage(), null, 0);
    }


    public AbstractException(ErrorMessage errorMessage, String message) {
        this(ensureErrorMessageNotNull(errorMessage), message, null, 0);
    }

    public AbstractException(ErrorMessage errorMessage, Throwable cause) {
        this(ensureErrorMessageNotNull(errorMessage), errorMessage.defaultMessage(), cause, 0);
    }

    public AbstractException(ErrorMessage errorMessage, String message, Throwable cause) {
        this(ensureErrorMessageNotNull(errorMessage), message, cause, 0);
    }

    private AbstractException(ErrorMessage errorMessage, String message, Throwable cause, int dummyParameter) {
        super(message, cause);
        this.errorMessage = errorMessage;
    }


    private static ErrorMessage ensureErrorMessageNotNull(ErrorMessage errorMessage) {
        return Assert.notNull(errorMessage, "ErrorMessage in AbstractException cannot be null!");
    }

}

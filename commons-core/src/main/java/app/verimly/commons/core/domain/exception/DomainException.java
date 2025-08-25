package app.verimly.commons.core.domain.exception;

import lombok.Getter;

/**
 * Abstract base class for all domain-specific exceptions.
 * <p>
 * Encapsulates an {@link ErrorMessage} object that provides error details.
 * Supports multiple constructors for flexibility in exception creation.
 * </p>
 */
@Getter
public abstract class DomainException extends RuntimeException {
    private final ErrorMessage errorMessage;

    /**
     * Constructs a new DomainException with an unknown error message and no detail message.
     */
    public DomainException() {
        this(ErrorMessage.unknown(),ErrorMessage.unknown().defaultMessage() , null);
    }

    /**
     * Constructs a new DomainException with an unknown error message and the specified detail message.
     *
     * @param message the detail message
     */
    public DomainException(String message) {
        this(ErrorMessage.unknown(), message, null);
    }

    /**
     * Constructs a new DomainException with the specified error message.
     *
     * @param errorMessage the error message object
     */
    public DomainException(ErrorMessage errorMessage) {
        this(errorMessage, controlErrorMessage(errorMessage).defaultMessage(), null);
    }

    /**
     * Constructs a new DomainException with the specified error message and detail message.
     *
     * @param errorMessage the error message object
     * @param message      the detail message
     */
    public DomainException(ErrorMessage errorMessage, String message) {
        this(errorMessage, message, null);
    }

    /**
     * Constructs a new DomainException with the specified error message, detail message, and cause.
     *
     * @param errorMessage the error message object
     * @param message      the detail message
     * @param cause        the cause of the exception
     */
    public DomainException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = controlErrorMessage(errorMessage);
    }

    /**
     * Returns the provided error message if not null, otherwise returns {@link ErrorMessage#unknown()}.
     *
     * @param errorMessage the error message to check
     * @return a non-null error message
     */
    private static ErrorMessage controlErrorMessage(ErrorMessage errorMessage) {
        if (errorMessage == null)
            return ErrorMessage.unknown();

        return errorMessage;
    }

}

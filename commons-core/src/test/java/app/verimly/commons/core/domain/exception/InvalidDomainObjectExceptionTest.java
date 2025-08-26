package app.verimly.commons.core.domain.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class InvalidDomainObjectExceptionTest {

    private static final ErrorMessage UNKNOWN_ERROR_MESSAGE = ErrorMessage.unknown();
    public static final String DEBUGGING_MESSAGE = "Message";
    public static final String DEFAULT_MESSAGE_STRING = "DEFAULT MESSAGE";
    public static final String ERROR_MESSAGE_CODE = "email.format";
    public static final ErrorMessage VALID_ERROR_MESSAGE = ErrorMessage.of(ERROR_MESSAGE_CODE, DEFAULT_MESSAGE_STRING);
    public static final IllegalArgumentException CAUSE = new IllegalArgumentException("cause");

    @Test
    public void constructWithString_message_thenUnknownErroMessage() {

        InvalidDomainObjectException exception = new InvalidDomainObjectException(DEBUGGING_MESSAGE);

        ErrorMessage actual = exception.getErrorMessage();
        assertEquals(UNKNOWN_ERROR_MESSAGE, actual);
        assertEquals(DEBUGGING_MESSAGE, exception.getMessage());
    }

    @Test
    public void constructWithNoArg_thenUnknownErrorMessage() {
        InvalidDomainObjectException exception = new InvalidDomainObjectException();

        ErrorMessage actual = exception.getErrorMessage();
        assertEquals(UNKNOWN_ERROR_MESSAGE, actual);
        assertEquals(UNKNOWN_ERROR_MESSAGE.defaultMessage(), exception.getMessage());

    }

    @Test
    public void constructWithErrorMessage_whenNullErrorMessage_thenThrowsIllegalArgumentException() {
        ErrorMessage nullErrorMessage = null;


        Executable action = () -> new InvalidDomainObjectException(nullErrorMessage);


        assertThrows(IllegalArgumentException.class, action);

    }

    @Test
    public void constructWithErrorMessage_validErrorMessage_exceptionMessageEqualsToDefaultMessage() {
        ErrorMessage expected = VALID_ERROR_MESSAGE;

        InvalidDomainObjectException exception = new InvalidDomainObjectException(VALID_ERROR_MESSAGE);

        ErrorMessage actual = exception.getErrorMessage();
        assertEquals(expected.code(), actual.code());
        assertEquals(expected.defaultMessage(), actual.defaultMessage());
        assertEquals(expected.defaultMessage(), exception.getMessage());
    }

    @Test
    public void constructWithErrorMessageAndDebuggingMessage_validErrorMessage_exceptionMessageEqualToDebuggingMessage() {

        ErrorMessage expected = VALID_ERROR_MESSAGE;

        InvalidDomainObjectException exception = new InvalidDomainObjectException(VALID_ERROR_MESSAGE, DEBUGGING_MESSAGE);

        ErrorMessage actual = exception.getErrorMessage();
        assertEquals(expected.code(), actual.code());
        assertEquals(expected.defaultMessage(), actual.defaultMessage());
        assertEquals(DEBUGGING_MESSAGE, exception.getMessage());
    }

    @Test
    public void constructWithErrorMessageAndDebuggingMessageAndCause_validErrorMessage_exceptionMessageEqualToDebuggingMessageAndCauseNotNull() {
        ErrorMessage expected = VALID_ERROR_MESSAGE;


        InvalidDomainObjectException exception = new InvalidDomainObjectException(expected, DEBUGGING_MESSAGE, CAUSE);

        ErrorMessage actual = exception.getErrorMessage();
        assertEquals(expected.code(), actual.code());
        assertEquals(expected.defaultMessage(), actual.defaultMessage());
        assertEquals(DEBUGGING_MESSAGE, exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals(CAUSE, exception.getCause());
    }
}

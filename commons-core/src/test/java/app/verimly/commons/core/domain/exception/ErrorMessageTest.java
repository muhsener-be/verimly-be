package app.verimly.commons.core.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ErrorMessageTest {

    public static final String EMPTY_STRING = "";
    public static final String VALID_CODE = "valid.code";

    @Test
    public void of_withNullCode_thenThrowsIllegalArgumentException() {
        String nullCode = null;
        String defaultMessage = "Default Message";

        assertThrows(IllegalArgumentException.class,
                () -> ErrorMessage.of(nullCode, defaultMessage));
    }

    @Test
    public void of_withNullDefaultMessage_thenReturnEmptyDefaultMessage() {

        String nullDefaultMessage = null;

        ErrorMessage actual = ErrorMessage.of(VALID_CODE, nullDefaultMessage);

        assertEquals(EMPTY_STRING, actual.defaultMessage());
        assertEquals(VALID_CODE,actual.code());
    }


}

package app.verimly.commons.core.domain.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputValidatorTest {

    public static final String BLANK_STRING = "  ";
    private final String VALID_EMAIL_VALUE = "test@test.com";
    private final String INVALID_EMAIL_VALUE = "testtest.com";


    @Test
    public void isEmailValid_validEmail_true() {
        boolean result = InputValidator.isEmailValid(VALID_EMAIL_VALUE);
        assertTrue(result);
    }

    @Test
    public void isEmailValid_invalidEmail_false(){
        boolean result = InputValidator.isEmailValid(INVALID_EMAIL_VALUE);
        assertFalse(result);
    }

    @Test
    public void isEmailValid_nullValue_false(){
        boolean result = InputValidator.isEmailValid(null);
        assertFalse(result);
    }

    @Test
    public void isEmailValid_blankValue_false(){
        boolean result = InputValidator.isEmailValid(BLANK_STRING);
        assertFalse(result);
    }



}

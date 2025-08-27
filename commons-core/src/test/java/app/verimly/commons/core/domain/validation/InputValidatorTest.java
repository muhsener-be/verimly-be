package app.verimly.commons.core.domain.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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
    public void isEmailValid_invalidEmail_false() {
        boolean result = InputValidator.isEmailValid(INVALID_EMAIL_VALUE);
        assertFalse(result);
    }

    @Test
    public void isEmailValid_nullValue_false() {
        boolean result = InputValidator.isEmailValid(null);
        assertFalse(result);
    }

    @Test
    public void isEmailValid_blankValue_false() {
        boolean result = InputValidator.isEmailValid(BLANK_STRING);
        assertFalse(result);
    }


    @Test
    public void isColorValid_nullValue_false() {
        boolean result = InputValidator.isColorValid(null);

        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("supplyValidHex")
    public void isColorValid_validValues_true(String validHex) {
        boolean result = InputValidator.isColorValid(validHex);

        assertTrue(result);
    }


    @ParameterizedTest
    @MethodSource("supplyInvalidHex")
    public void isColorValid_invalidValues_false(String invalidHex){
        boolean result = InputValidator.isColorValid(invalidHex);

        assertFalse(result);
    }

    static Stream<String> supplyValidHex() {
        return Stream.of(
            "#FFF",
            "#ffffff",
            "#000",
            "#123456",
            "#ABCDEF",
            "#abc",
            "#789abc",
            "#A1B2C3"
        );
    }

    static Stream<String> supplyInvalidHex() {
        return Stream.of(
            null,
            "",
            "FFF",
            "#FFFF",
            "#GGG",
            "#12345G",
            "#1234567",
            "#12",
            "#abcd",
            "#12345",
            "#12345678",
            "123456",
            "#12G",
            "#",
            " #FFF",
            "#FFF ",
            "#F F F"
        );
    }

}

package app.verimly.commons.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyStringUtilsTest {

    public static final int NEGATIVE_INTEGER = -10;
    public static final int STRING_LENGTH = 100;

    @Test
    public void generateString_whenLengthIsNegative_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> MyStringUtils.generateString(NEGATIVE_INTEGER));
    }

    @Test
    public void generateString_happy_path() {
        String actual = MyStringUtils.generateString(STRING_LENGTH);

        assertEquals(STRING_LENGTH, actual.length());
    }

    @Test
    public void generateString_whenLengthIsZero_thenReturnsEmptyString() {
        String actual = MyStringUtils.generateString(0);
        assertEquals("", actual);
    }

}
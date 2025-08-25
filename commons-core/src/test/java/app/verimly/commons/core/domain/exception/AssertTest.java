package app.verimly.commons.core.domain.exception;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AssertTest {

    public static final String AN_OBJECT = "anObject";
    public static final String MESSAGE = "message provided.";
    public static final Object NULL_OBJECT = null;
    public static final String BLANK_STRING = "   ";

    @Test
    public void notNull_whenValueIsNotNull_doesNotThrowAndReturnValue() {
        // Arrange
        String expected = AN_OBJECT;

        //Act
        assertDoesNotThrow(() -> Assert.notNull(AN_OBJECT, MESSAGE));
        String actual = Assert.notNull(expected, MESSAGE);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void notNull_whenValueIsNull_throws() {
        // Arrange

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Assert.notNull(NULL_OBJECT, MESSAGE));

        //Assert
        assertEquals(MESSAGE, exception.getMessage());

    }


    @Test
    public void notBlank_whenValueIsNull_throws() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Assert.notBlank(null, MESSAGE));

        assertEquals(MESSAGE, exception.getMessage());
    }

    @Test
    public void notBlank_whenValueIsBlank_throws() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Assert.notBlank(BLANK_STRING, MESSAGE));

        assertEquals(MESSAGE, exception.getMessage());
    }

}

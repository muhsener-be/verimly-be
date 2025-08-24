package app.verimly.commons.core.domain.exception;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AssertTest {

    public static final String AN_OBJECT = "anObject";
    public static final String MESSAGE = "anObject cannot be null";
    public static final Object NULL_OBJECT = null;

    @Test
    public void notNull_whenValueIsNotNull_doesNotThrowAndReturnValue() {
        // Arrange
        String expected = AN_OBJECT;

        //Act
        assertDoesNotThrow(() -> Assert.notNull(AN_OBJECT,MESSAGE));
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

}

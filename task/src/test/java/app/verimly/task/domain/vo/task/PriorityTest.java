package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PriorityTest {

    @ParameterizedTest
    @EnumSource(Priority.class)
    void values_shouldContainAllPriorityValues(Priority priority) {
        assertNotNull(priority);
        assertTrue(Priority.values().length > 0);
    }

    @Test
    void values_shouldContainExactlyThreeValues() {
        Priority[] values = Priority.values();
        assertEquals(3, values.length);
        assertArrayEquals(new Priority[]{Priority.LOW, Priority.MEDIUM, Priority.HIGH}, values);
    }

    @ParameterizedTest
    @MethodSource("validPriorityStrings")
    void of_shouldReturnCorrectPriorityForValidString(String input, Priority expected) {
        Priority result = Priority.of(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"low", "Low", "lOw", "MEDIUM", "medium", "Medium", "HIGH", "high", "High"})
    void of_shouldBeCaseInsensitive(String input) {
        Priority result = Priority.of(input);
        assertNotNull(result);
        assertEquals(input.toUpperCase(), result.name());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void of_shouldReturnNullForNullOrBlankInput(String input) {
        assertNull(Priority.of(input));
    }

    @Test
    void of_shouldThrowExceptionForInvalidPriority() {
        String invalidPriority = "INVALID";
        InvalidDomainObjectException exception = assertThrows(
            InvalidDomainObjectException.class,
            () -> Priority.of(invalidPriority)
        );
        assertEquals("Unknown priority type: " + invalidPriority, exception.getMessage());
    }

    private static Stream<Arguments> validPriorityStrings() {
        return Stream.of(
            Arguments.of("LOW", Priority.LOW),
            Arguments.of("low", Priority.LOW),
            Arguments.of("Low", Priority.LOW),
            Arguments.of("MEDIUM", Priority.MEDIUM),
            Arguments.of("medium", Priority.MEDIUM),
            Arguments.of("Medium", Priority.MEDIUM),
            Arguments.of("HIGH", Priority.HIGH),
            Arguments.of("high", Priority.HIGH),
            Arguments.of("High", Priority.HIGH)
        );
    }
}
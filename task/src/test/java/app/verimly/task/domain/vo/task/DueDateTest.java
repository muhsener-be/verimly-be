package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DueDateTest {

    @Test
    void of_whenValueIsNull_thenReturnsNull() {
        DueDate dueDate = DueDate.of(null);
        assertNull(dueDate);
    }

    @Test
    void of_whenValueIsInThePast_thenThrowsInvalidDomainObjectException() {
        Instant past = Instant.now().minus(1, ChronoUnit.HOURS);
        assertDueDateThrowsPastException(past);
    }

    @Test
    void of_whenValueIsCurrentTime_thenThrowsInvalidDomainObjectException() {
        // Using now() as the exact current time might be too strict, but it's included for completeness
        assertDueDateThrowsPastException(Instant.now());
    }

    @Test
    void of_whenValueIsInTheFuture_thenCreatesDueDate() {
        Instant future = Instant.now().plus(1, ChronoUnit.HOURS);
        DueDate dueDate = DueDate.of(future);
        assertNotNull(dueDate);
        assertEquals(future, dueDate.getValue());
    }

    @Test
    void of_whenValueIsFarInTheFuture_thenCreatesDueDate() {
        Instant farFuture = Instant.now().plus(365, ChronoUnit.DAYS);
        DueDate dueDate = DueDate.of(farFuture);
        assertNotNull(dueDate);
        assertEquals(farFuture, dueDate.getValue());
    }

    @Test
    void of_whenValueIsJustOneMillisecondInFuture_thenCreatesDueDate() {
        Instant justInFuture = Instant.now().plusMillis(1);
        DueDate dueDate = DueDate.of(justInFuture);
        assertNotNull(dueDate);
        assertEquals(justInFuture, dueDate.getValue());
    }

    @Test
    void of_whenCalledMultipleTimesWithSameValue_thenCreatesEqualObjects() {
        Instant future = Instant.now().plus(1, ChronoUnit.HOURS);
        DueDate dueDate1 = DueDate.of(future);
        DueDate dueDate2 = DueDate.of(future);

        assertNotSame(dueDate1, dueDate2); // Different instances
        assertEquals(dueDate1, dueDate2);  // But equal in value
        assertEquals(dueDate1.hashCode(), dueDate2.hashCode());
    }

    @Test
    void equalsAndHashCode_whenComparedWithDifferentTypes_thenNotEqual() {
        Instant future = Instant.now().plus(1, ChronoUnit.HOURS);
        DueDate dueDate = DueDate.of(future);
        assertNotEquals(dueDate, future);
        assertEquals(dueDate.hashCode(), future.hashCode());
    }


    @ParameterizedTest
    @MethodSource("provideInvalidInstants")
    void of_withInvalidInstants_throwsException(Instant invalidInstant) {
        assertDueDateThrowsPastException(invalidInstant);
    }

    private static Stream<Arguments> provideInvalidInstants() {
        return Stream.of(
                Arguments.of(Instant.now().minus(1, ChronoUnit.SECONDS)),
                Arguments.of(Instant.now().minus(1, ChronoUnit.MINUTES)),
                Arguments.of(Instant.now().minus(1, ChronoUnit.HOURS)),
                Arguments.of(Instant.now().minus(1, ChronoUnit.DAYS))
        );
    }


    private void assertDueDateThrowsPastException(Instant instant) {
        InvalidDomainObjectException exception = assertThrows(
                InvalidDomainObjectException.class,
                () -> DueDate.of(instant)
        );
        assertEquals(DueDate.Errors.PAST, exception.getErrorMessage());
    }


    @ParameterizedTest
    @MethodSource("provideInvalidInstants")
    void reconstruct_shouldDoNotCheckInvariants(Instant instant) {
        assertDoesNotThrow(() -> DueDate.reconstruct(instant));
    }


}
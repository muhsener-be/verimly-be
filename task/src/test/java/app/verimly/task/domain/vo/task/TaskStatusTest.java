package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskStatusTest {

    @Test
    void of_whenNullOrBlankValue_thenReturnsNull() {
        TaskStatus nullStatus = TaskStatus.of(null);
        TaskStatus blankStatus = TaskStatus.of("   ");

        assertNull(nullStatus);
        assertNull(blankStatus);
    }

    @Test
    void of_whenUnknown_thenThrows() {
        assertThrows(InvalidDomainObjectException.class, () -> TaskStatus.of("Unknown"));
    }

    @Test
    void of_whenInProgress_thenIN_PROGRESS(){
        String value = "In_progress";

        TaskStatus taskStatus = TaskStatus.of(value);

        assertEquals(TaskStatus.IN_PROGRESS , taskStatus);
    }

    @Test
    void of_whenDone_thenDONE() {
        String value = "done";

        TaskStatus taskStatus = TaskStatus.of(value);

        assertEquals(TaskStatus.DONE , taskStatus);
    }

    @Test
    void of_whenNotStarted_thenNOT_STARTED() {
        String value = "not_starTed";

        TaskStatus taskStatus = TaskStatus.of(value);

        assertEquals(TaskStatus.NOT_STARTED , taskStatus);
    }
}
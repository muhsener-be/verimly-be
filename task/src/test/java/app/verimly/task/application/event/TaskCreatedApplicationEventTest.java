package app.verimly.task.application.event;

import app.verimly.commons.core.security.Principal;
import app.verimly.task.data.SecurityTestData;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TaskCreatedApplicationEventTest {

    SecurityTestData securityTestData = SecurityTestData.getInstance();
    TaskTestData taskTestData = TaskTestData.getInstance();

    Principal actor;
    Task task;

    @BeforeEach
    public void setup() {
        actor = securityTestData.authenticatedPrincipal();
        task = taskTestData.task();

    }


    @Test
    void construct_whenActorIsNull_thenThrowsIllegalArgumentException() {
        actor = null;
        assertThrowsIllegalArgumentException();
    }

    @Test
    void construct_whenTaskIsNull_thenThrowsIllegalArgumentException() {
        task = null;
        assertThrowsIllegalArgumentException();
    }

    @Test
    void construct_whenArgumentsIsValid_shouldOccurredAtMustBeValid() throws Throwable {

        TaskCreatedApplicationEvent event = new TaskCreatedApplicationEvent(actor, task);

        assertNotNull(event.getOccurredAt());
        Instant occurredAt = event.getOccurredAt();

        Instant now = Instant.now();
        Duration between = Duration.between(occurredAt, now);
        assertTrue(between.getSeconds() < 1);

    }


    private void assertThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, getConstructExecutable());
    }

    private Executable getConstructExecutable() {
        return () -> new TaskCreatedApplicationEvent(actor, task);
    }

}
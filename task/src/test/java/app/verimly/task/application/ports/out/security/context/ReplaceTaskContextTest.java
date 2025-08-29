package app.verimly.task.application.ports.out.security.context;

import app.verimly.task.domain.vo.task.TaskId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReplaceTaskContextTest {

    TaskId taskId = TaskId.random();

    @Test
    void of_whenTaskIdNull_thenThrowsIllegalArgumentException() {
        taskId = null;

        assertThrows(IllegalArgumentException.class, () -> ReplaceTaskContext.createWithTaskId(taskId));
    }

    @Test
    void of_whenTaskIdValid_thenSuccess() {

        ReplaceTaskContext context = ReplaceTaskContext.createWithTaskId(taskId);

        assertEquals(taskId, context.getTaskId());

    }
}
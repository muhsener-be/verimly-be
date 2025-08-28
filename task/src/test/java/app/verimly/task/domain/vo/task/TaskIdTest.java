package app.verimly.task.domain.vo.task;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TaskIdTest{

    @Test
    void of_whenValueIsNull_thenReturnsNull() {
       TaskId actual =  TaskId.of(null);

       assertNull(actual);
    }

    @Test
    void of_whenValidUUID_thenConstructSuccessfully(){
        UUID validUUID = UUID.randomUUID();

        TaskId taskId = TaskId.of(validUUID);

        assertEquals(validUUID, taskId.getValue());

    }

    @Test
    void random_shouldCreatesRandomUUID(){
        TaskId actual = TaskId.random();

        assertNotNull(actual.getValue());
    }
}

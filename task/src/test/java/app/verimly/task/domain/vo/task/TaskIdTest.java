package app.verimly.task.domain.vo.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskIdTest{

    @Test
    void of_whenValueIsNull_thenReturnsNull() {
       TaskId actual =  TaskId.of(null);

       assertNull(actual);
    }



}

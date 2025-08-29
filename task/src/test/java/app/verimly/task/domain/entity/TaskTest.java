package app.verimly.task.domain.entity;

import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.vo.task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {


    private static TaskTestData DATA = TaskTestData.getInstance();

    Task task;
    TaskName newName;
    TaskStatus newStatus;
    TaskDescription description;
    DueDate dueDate;
    Priority priority;

    @BeforeEach()
    void setup() {
        task = DATA.taskWithPriority(Priority.LOW);
        newName = TaskName.of("New Task name");
        newStatus = TaskStatus.DONE;
        description = TaskDescription.of("New description for test");
        dueDate = DATA.dueDate();
        priority = Priority.HIGH;

    }

    @Nested
    @DisplayName("Rename Tasks")
    class Rename {
        @Test
        void rename_whenNewNameIsNull_thenThrowsTaskDomainException() {
            // ARRANGE
            TaskName newName = null;

            // ACT AND ASSERT
            assertThrows(TaskDomainException.class, () -> task.rename(newName));
        }

        @Test
        void rename_whenNameIsValid_thenChangesName() {
            // ACT
            task.rename(newName);
            // ASSERT
            assertEquals(newName, task.getName());
        }
    }


    @Nested
    @DisplayName("Change Status")
    class ChangeStatus {

        @Test
        void whenNewStatusIsNull_thenThrowsTasDomainException() {
            newStatus = null;

            assertThrows(TaskDomainException.class, () -> task.changeStatus(newStatus));
        }

        @Test
        void whenNewStatuValid_thenChangesStatus() {
            assertNotEquals(newStatus, task.getStatus());

            task.changeStatus(newStatus);

            assertEquals(newStatus, task.getStatus());
        }
    }

    @Nested
    @DisplayName("Change Description")
    class ChangeDescription {
        @Test
        void success() {
            assertNotEquals(description, task.getDescription());


            task.changeDescription(description);

            assertEquals(description, task.getDescription());
        }
    }

    @Nested
    @DisplayName("Change DueDate")
    class ChangeDueDate {
        @Test
        void success() {
            assertNotEquals(dueDate, task.getDueDate());

            task.changeDueDate(dueDate);

            assertEquals(dueDate, task.getDueDate());
        }
    }

    @Nested
    @DisplayName("Change Priority")
    class ChangePriority {
        @Test
        void success() {
            assertNotEquals(priority, task.getPriority());

            task.changePriority(priority);

            assertEquals(priority, task.getPriority());
        }
    }


}
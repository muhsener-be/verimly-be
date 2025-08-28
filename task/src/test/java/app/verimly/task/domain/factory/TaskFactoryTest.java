package app.verimly.task.domain.factory;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TaskFactory.class)
class TaskFactoryTest {

    TaskTestData DATA = TaskTestData.getInstance();
    UserId ownerId;
    FolderId folderId;
    TaskName name;
    TaskDescription description;
    DueDate dueDate;
    Priority priority;

    @Autowired
    TaskFactory factory;

    @BeforeEach
    void setup() {
        ownerId = DATA.ownerId();
        folderId = DATA.folderId();
        name = DATA.name();
        description = DATA.description();
        dueDate = DATA.dueDate();
        priority = DATA.priority();
    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(factory);
    }

    @Test
    void create_whenAllArgumentsValid_thenCreateATaskWithIdAndNotStartedStatus() {
        Task task = callFactoryCreateMethod();

        assertNotNull(task.getId());
        assertEquals(TaskStatus.NOT_STARTED, task.getStatus());
    }

    @Test
    void create_whenOwnerIdIsNull_thenThrows() {
        ownerId = null;

        assertThrowsTaskDomainException();
    }

    @Test
    void create_whenFolderIdIsNull_thenThrows() {
        folderId = null;

        assertThrowsTaskDomainException();
    }

    @Test
    void create_whenNameIsNull_thenThrows(){
        name = null;

        assertThrowsTaskDomainException();
    }

    @Test
    void create_whenDescriptionIsNull_doesNotThrow(){
        description = null;

        assertDoesNotThrow(this::callFactoryCreateMethod);
    }

    @Test
    void create_whenPriorityIsNull_doesNotThrow(){
        priority = null;

        assertDoesNotThrow(this::callFactoryCreateMethod);
    }

    @Test
    void create_whenDueDateIsNull_doesNotThrow(){
        dueDate = null;

        assertDoesNotThrow(this::callFactoryCreateMethod);
    }








    private void assertThrowsTaskDomainException() {
        assertThrows(TaskDomainException.class, this::callFactoryCreateMethod);
    }

    private Task callFactoryCreateMethod() {
        return factory.create(ownerId, folderId, name, description, dueDate, priority);
    }


}
package app.verimly.task.application.usecase.command.task.create;

import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.DueDate;
import app.verimly.task.domain.vo.task.Priority;
import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateTaskCommandTest {

    TaskTestData DATA = TaskTestData.getInstance();
    TaskName name = DATA.name();
    TaskDescription description = DATA.description();
    DueDate dueDate = DATA.dueDate();
    FolderId folderId = DATA.folderId();
    Priority priority = DATA.priority();


    @Test
    void construct_validArguments_thenConstructs() {
        CreateTaskCommand command = new CreateTaskCommand(folderId, name, description, priority, dueDate);
        assertEquals(name, command.name());
        assertEquals(description, command.description());
        assertEquals(folderId, command.folderId());
        assertEquals(dueDate, command.dueDate());
        assertEquals(priority, command.priority());
    }

}

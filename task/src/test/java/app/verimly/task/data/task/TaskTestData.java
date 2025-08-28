package app.verimly.task.data.task;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.utils.MyStringUtils;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.input.TaskCreationDetails;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.*;
import com.github.javafaker.Faker;

import java.util.concurrent.TimeUnit;

public class TaskTestData {

    private static final Faker FAKER = new Faker();

    private static final TaskTestData INSTANCE = new TaskTestData();

    public static TaskTestData getInstance() {
        return INSTANCE;
    }


    public String tooLongNameValue() {
        return MyStringUtils.generateString(TaskName.MAX_LENGTH + 1);
    }

    public String tooLongDescriptionValue() {
        return MyStringUtils.generateString(TaskDescription.MAX_LENGTH + 1);
    }


    public TaskId id() {
        return TaskId.random();
    }

    public TaskName name() {
        return TaskName.of(FAKER.job().keySkills());
    }

    public Task task() {
        return Task.builder()
                .id(id())
                .name(name())
                .description(description())
                .dueDate(dueDate())
                .folderId(folderId())
                .ownerId(ownerId())
                .priority(priority())
                .status(TaskStatus.NOT_STARTED)
                .build();
    }

    public FolderId folderId() {
        return FolderId.random();
    }

    public Priority priority() {
        int random = ((int) (Math.random() * Priority.values().length));
        return Priority.values()[random];
    }

    public UserId ownerId() {
        return UserId.random();
    }


    public DueDate dueDate() {
        return DueDate.of(FAKER.date().future(2, TimeUnit.HOURS).toInstant());
    }

    public TaskDescription description() {
        return TaskDescription.of(FAKER.harryPotter().quote());
    }


    public CreateTaskCommand createTaskCommand() {
        return new CreateTaskCommand(folderId(), name(), description(), priority(), dueDate());
    }

    public TaskCreationDetails creationDetailsWithOwnerIdAndFolderId(UserId ownerId, FolderId folderId) {
        return TaskCreationDetails.of(ownerId, folderId, name(), description(), priority(), dueDate());
    }
}

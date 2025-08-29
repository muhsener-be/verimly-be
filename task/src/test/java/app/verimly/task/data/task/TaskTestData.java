package app.verimly.task.data.task;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.utils.MyStringUtils;
import app.verimly.task.adapter.persistence.entity.TaskEntity;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.event.TaskCreatedApplicationEvent;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.application.usecase.command.task.move_to_folder.MoveTaskToFolderCommand;
import app.verimly.task.application.usecase.command.task.replace.ReplaceTaskCommand;
import app.verimly.task.data.SecurityTestData;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.input.TaskCreationDetails;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.*;
import com.github.javafaker.Faker;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class TaskTestData {

    private static final Faker FAKER = new Faker();
    private static final SecurityTestData SECURITY_TEST_DATA = SecurityTestData.getInstance();

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

    public Task withOwnerId(UserId ownerId) {
        return task().toBuilder().ownerId(ownerId).build();
    }

    public Task taskWithId(TaskId taskId) {
        return task().toBuilder().id(taskId).build();
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


    public Task taskWithNullFields() {
        return Task.reconstruct(TaskId.random(), null, null, null, null, null, null, null);
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

    public TaskCreatedApplicationEvent taskCreatedApplicationEvent() {
        return new TaskCreatedApplicationEvent(SECURITY_TEST_DATA.authenticatedPrincipal(), task());
    }

    public TaskCreationDetails taskCreationDetails() {
        return TaskCreationDetails.of(ownerId(), folderId(), name(), description(), priority(), dueDate());
    }

    public TaskCreationResponse taskCreationResponse() {
        return new TaskCreationResponse(id(), folderId(), ownerId(), name(), description(), dueDate(), status(), priority());
    }

    public TaskStatus status() {
        int random = ((int) (Math.random() * TaskStatus.values().length));
        return TaskStatus.values()[random];
    }

    public TaskEntity taskEntityWithNullFields() {
        return new TaskEntity(null, null, null, null, null, null, null, null);
    }


    public List<TaskSummaryData> summaryDatas(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> summaryData())
                .toList();
    }

    public TaskSummaryData summaryData() {
        return new TaskSummaryData(
                id().getValue(), ownerId().getValue(), folderId().getValue(), name().getValue(),
                description().getValue(), dueDate().getValue(), status().name(), priority().name(),
                Instant.now(), Instant.now()
        );
    }


    public Task taskWithIdAndOwnerId(TaskId taskId, UserId ownerId) {
        return task().toBuilder().id(taskId).ownerId(ownerId).build();

    }

    public MoveTaskToFolderCommand moveToFolderCommand() {
        return new MoveTaskToFolderCommand(id(), folderId());
    }

    public Task taskWithPriority(Priority priority) {
        return task().toBuilder().priority(priority).build();
    }

    public ReplaceTaskCommand replaceTaskCommand() {
        return new ReplaceTaskCommand(id(), name(), description(), dueDate(), status(), priority());
    }
}

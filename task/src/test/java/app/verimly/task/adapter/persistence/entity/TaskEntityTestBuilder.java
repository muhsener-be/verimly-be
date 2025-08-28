package app.verimly.task.adapter.persistence.entity;

import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.vo.task.Priority;
import app.verimly.task.domain.vo.task.TaskStatus;

import java.time.Instant;
import java.util.UUID;

public class TaskEntityTestBuilder {
    private static final TaskTestData TASK_TEST_DATA = TaskTestData.getInstance();
    private UUID id;
    private UUID ownerId;
    private UUID folderId;
    private String name;
    private String description;
    private Instant dueDate;
    private TaskStatus status;
    private Priority priority;


    public TaskEntityTestBuilder() {
        this.id = TASK_TEST_DATA.id().getValue();
        this.ownerId = TASK_TEST_DATA.ownerId().getValue();
        this.folderId = TASK_TEST_DATA.folderId().getValue();
        this.name = TASK_TEST_DATA.name().getValue();
        this.description = TASK_TEST_DATA.description().getValue();
        this.dueDate = TASK_TEST_DATA.dueDate().getValue();
        this.status = TaskStatus.NOT_STARTED;
        this.priority = TASK_TEST_DATA.priority();
    }


    public TaskEntityTestBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public TaskEntityTestBuilder withOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public TaskEntityTestBuilder withFolderId(UUID folderId) {
        this.folderId = folderId;
        return this;
    }

    public TaskEntityTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TaskEntityTestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskEntityTestBuilder withDueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public TaskEntityTestBuilder withStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    public TaskEntityTestBuilder withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public TaskEntityTestBuilder withRandomId() {
        this.id = UUID.randomUUID();
        return this;
    }

    public TaskEntityTestBuilder withRandomOwnerId() {
        this.ownerId = UUID.randomUUID();
        return this;
    }

    public TaskEntityTestBuilder withRandomFolderId() {
        this.folderId = UUID.randomUUID();
        return this;
    }

    public TaskEntity build() {

        return TaskEntity.builder()
                .id(id)
                .ownerId(ownerId)
                .folderId(folderId)
                .name(name)
                .description(description)
                .dueDate(dueDate)
                .status(status)
                .priority(priority)
                .build();
    }
}

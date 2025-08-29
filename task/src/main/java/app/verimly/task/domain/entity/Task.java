package app.verimly.task.domain.entity;

import app.verimly.commons.core.domain.entity.BaseEntity;
import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Task extends BaseEntity<TaskId> {

    private UserId ownerId;
    private FolderId folderId;
    private TaskName name;
    private TaskDescription description;
    private DueDate dueDate;
    private TaskStatus status;
    private Priority priority;


    @Builder(toBuilder = true)
    private Task(TaskId id, UserId ownerId, FolderId folderId, TaskName name,
                 TaskDescription description, DueDate dueDate, TaskStatus status,
                 Priority priority) {
        this.id = id;
        this.ownerId = ownerId;
        this.folderId = folderId;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;

        if (this.id == null) initialize();


    }


    public void rename(TaskName newName) {
        if (Objects.equals(this.name, newName))
            return;

        this.name = newName;
        checkNameInvariant();
    }

    public void changeStatus(TaskStatus status) {
        if (Objects.equals(this.status, status))
            return;

        if (status == null)
            throw new TaskDomainException(Errors.STATUS_NOT_NULL);

        this.status = status;
    }

    public void changeDescription(TaskDescription description) {
        if (Objects.equals(this.description, description))
            return;
        this.description = description;
    }

    public void changeDueDate(DueDate dueDate) {
        if (Objects.equals(this.dueDate, dueDate))
            return;

        this.dueDate = dueDate;
    }

    public void changePriority(Priority priority) {
        if (Objects.equals(this.priority, priority))
            return;

        this.priority = priority;
    }


    public boolean isNotStarted() {
        return status == TaskStatus.NOT_STARTED;
    }


    private void initialize() {
        assert id == null : "To initialize task, ID must be null.";
        checkInvariants();
        this.id = TaskId.random();
        this.status = TaskStatus.NOT_STARTED;

    }

    private void checkInvariants() {
        if (ownerId == null)
            throw new TaskDomainException(Errors.OWNER_NOT_EXIST);

        if (folderId == null)
            throw new TaskDomainException(Errors.FOLDER_NOT_EXIST);

        checkNameInvariant();
    }

    private void checkNameInvariant() {
        if (name == null)
            throw new TaskDomainException(Errors.NAME_NOT_EXIST);
    }

    public static Task reconstruct(TaskId id, UserId ownerId, FolderId folderId, TaskName name,
                                   TaskDescription description, DueDate dueDate, TaskStatus status,
                                   Priority priority) {

        return new Task(id, ownerId, folderId, name, description, dueDate, status, priority);
    }

    public void moveToFolder(FolderId folderId) {
        Assert.notNull(folderId, "To move task to folder, folderId cannot be null.");
        this.folderId = folderId;
    }


    public static final class Errors {
        public static final ErrorMessage OWNER_NOT_EXIST = ErrorMessage.of("task.owner-not-exist", "Task must have an owner");
        public static final ErrorMessage FOLDER_NOT_EXIST = ErrorMessage.of("task.folder-not-exist", "Task must be in a folder.");
        public static final ErrorMessage NAME_NOT_EXIST = ErrorMessage.of("task.name-not-exist", "Task must have a name.");
        public static final ErrorMessage FOLDER_OWNER_NOT_MATCH = ErrorMessage.of("task.folder-owner-not-match", "Task owner and folder owner must match.");
        public static final ErrorMessage STATUS_NOT_NULL = ErrorMessage.of("task.status-not-null", "Task status cannot be null");
    }


}


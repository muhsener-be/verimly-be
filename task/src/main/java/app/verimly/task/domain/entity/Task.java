package app.verimly.task.domain.entity;

import app.verimly.commons.core.domain.entity.BaseEntity;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.*;
import lombok.Builder;
import lombok.Getter;

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

        if (name == null)
            throw new TaskDomainException(Errors.NAME_NOT_EXIST);


    }


    public static final class Errors {
        public static final ErrorMessage OWNER_NOT_EXIST = ErrorMessage.of("task.owner-not-exist", "Task must have an owner");
        public static final ErrorMessage FOLDER_NOT_EXIST = ErrorMessage.of("task.folder-not-exist", "Task must be in a folder.");
        public static final ErrorMessage NAME_NOT_EXIST = ErrorMessage.of("task.name-not-exist", "Task must have a name.");
    }


}


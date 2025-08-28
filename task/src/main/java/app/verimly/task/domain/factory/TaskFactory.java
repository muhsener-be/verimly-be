package app.verimly.task.domain.factory;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.DueDate;
import app.verimly.task.domain.vo.task.Priority;
import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskName;

public class TaskFactory {


    public Task create(UserId ownerId, FolderId folderId, TaskName name, TaskDescription description,
                       DueDate dueDate, Priority priority) throws TaskDomainException {
        return Task.builder()
                .ownerId(ownerId)
                .folderId(folderId)
                .name(name)
                .description(description)
                .dueDate(dueDate)
                .priority(priority)
                .build();
    }
}

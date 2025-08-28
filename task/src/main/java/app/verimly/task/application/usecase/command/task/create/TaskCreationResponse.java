package app.verimly.task.application.usecase.command.task.create;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.*;

public record TaskCreationResponse(TaskId id, FolderId folderId, UserId ownerId, TaskName name,
                                   TaskDescription description,
                                   DueDate dueDate, TaskStatus status, Priority priority) {

    public TaskCreationResponse withDueDate(DueDate dueDate) {
        return new TaskCreationResponse(id, folderId, ownerId, name, description, dueDate, status, priority);
    }
}

package app.verimly.task.domain.input;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.DueDate;
import app.verimly.task.domain.vo.task.Priority;
import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskName;

public record TaskCreationDetails(UserId ownerId, FolderId folderId, TaskName name, TaskDescription description,
                                  Priority priority, DueDate dueDate) {


    public static TaskCreationDetails of(UserId ownerId,
                                         FolderId folderId,
                                         TaskName name,
                                         TaskDescription description,
                                         Priority priority,
                                         DueDate dueDate) {
        return new TaskCreationDetails(ownerId, folderId, name, description, priority, dueDate);
    }

    public TaskCreationDetails withFolderId(FolderId folderId) {
        return of(this.ownerId, folderId, this.name, this.description, this.priority, this.dueDate);
    }

    public TaskCreationDetails withOwnerId(UserId ownerId) {
        return of(ownerId, this.folderId , this.name , this.description , this.priority , this.dueDate);
    }
}

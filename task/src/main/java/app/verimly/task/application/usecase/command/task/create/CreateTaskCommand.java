package app.verimly.task.application.usecase.command.task.create;

import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.DueDate;
import app.verimly.task.domain.vo.task.Priority;
import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskName;

public record CreateTaskCommand(FolderId folderId, TaskName name, TaskDescription description,
                                Priority priority, DueDate dueDate) {

}

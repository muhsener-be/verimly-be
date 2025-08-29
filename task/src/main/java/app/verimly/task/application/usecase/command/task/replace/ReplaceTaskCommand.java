package app.verimly.task.application.usecase.command.task.replace;

import app.verimly.task.domain.vo.task.*;

public record ReplaceTaskCommand(TaskName name, TaskDescription description, DueDate dueDate, TaskStatus status,
                                 Priority priority) {


}

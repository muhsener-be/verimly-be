package app.verimly.task.application.usecase.command.session.start;

import app.verimly.task.domain.vo.session.SessionName;
import app.verimly.task.domain.vo.task.TaskId;


public record StartSessionForTaskCommand(SessionName name, TaskId taskId) {


    public StartSessionForTaskCommand withTaskId(TaskId taskId) {
        return new StartSessionForTaskCommand(this.name, taskId);
    }
}

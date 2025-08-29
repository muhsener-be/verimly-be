package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.security.AuthorizationContext;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.Getter;

@Getter
public abstract class TaskAuthorizationContext implements AuthorizationContext {
    private TaskId taskId;

    public TaskAuthorizationContext() {
    }

    public TaskAuthorizationContext(TaskId taskId) {
        this.taskId = taskId;
    }


}

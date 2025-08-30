package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.Getter;

@Getter
public class StartSessionContext extends SessionAuthorizationContext {
    private TaskId taskId;

    private StartSessionContext(TaskId taskId) {
        super();
        this.taskId = taskId;
    }


    public static StartSessionContext createWithTaskId(TaskId taskId) {
        Assert.notNull(taskId, "taskId cannot be null to construct StartSessionContext.");
        return new StartSessionContext(taskId);

    }
}

package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.task.domain.vo.task.TaskId;

public class ReplaceTaskContext extends TaskAuthorizationContext {

    protected ReplaceTaskContext(TaskId taskId) {
        super(taskId);
    }

    public static ReplaceTaskContext createWithTaskId(TaskId taskId) {
        Assert.notNull(taskId, "TaskId cannot be null to construct ReplaceTaskContext.");
        return new ReplaceTaskContext(taskId);
    }
}

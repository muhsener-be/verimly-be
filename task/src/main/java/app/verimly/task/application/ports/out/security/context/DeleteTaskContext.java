package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.task.domain.vo.task.TaskId;

public class DeleteTaskContext extends TaskAuthorizationContext {


    protected DeleteTaskContext(TaskId taskId) {
        super(taskId);
    }

    public static DeleteTaskContext createWithTaskId(TaskId taskId) {
        Assert.notNull(taskId, "TaskId cannot be null when construct DeleteTaskContext");
        return new DeleteTaskContext(taskId);
    }
}

package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.task.domain.vo.task.TaskId;

public class ViewTaskContext extends TaskAuthorizationContext {

    private ViewTaskContext(TaskId taskId) {
        super(taskId);
    }

    public static ViewTaskContext create(TaskId taskIdToBeViewed) {
        Assert.notNull(taskIdToBeViewed, "taskId cannot be null to construct ViewTaskContext");
        return new ViewTaskContext(taskIdToBeViewed);
    }
}

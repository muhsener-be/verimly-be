package app.verimly.task.application.usecase.query.task.fetch_with_sessions;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.dto.TaskWithSessionsData;
import app.verimly.task.application.exception.TaskNotFoundException;
import app.verimly.task.application.ports.out.persistence.TaskReadRepository;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.ViewTaskContext;
import app.verimly.task.application.usecase.query.task.AbstractTaskQueryHandler;
import app.verimly.task.domain.vo.task.TaskId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FetchTaskWithSessionsQueryHandler extends AbstractTaskQueryHandler {

    private final TaskAuthorizationService taskAuthorizationService;
    private final TaskReadRepository repository;

    public FetchTaskWithSessionsQueryHandler(AuthenticationService authN,
                                             TaskAuthorizationService taskAuthorizationService,
                                             TaskReadRepository repository) {
        super(authN);
        this.taskAuthorizationService = taskAuthorizationService;
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public TaskWithSessionsData handle(TaskId taskId) {
        Assert.notNull(taskId, "taskId cannot be null to fetch task with session");
        Principal principal = super.getCurrentPrincipal();
        authorizeRequest(principal, taskId);


        return fetchTaskAndCheckExistence(taskId);

    }

    private TaskWithSessionsData fetchTaskAndCheckExistence(TaskId taskId) {
        return repository.fetchTaskDetailsWithSessionsById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    private void authorizeRequest(Principal principal, TaskId taskId) {
        ViewTaskContext viewTaskContext = ViewTaskContext.create(taskId);
        taskAuthorizationService.authorizeViewTask(principal, viewTaskContext);
    }
}

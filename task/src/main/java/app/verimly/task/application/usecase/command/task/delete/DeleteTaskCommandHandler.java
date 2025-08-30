package app.verimly.task.application.usecase.command.task.delete;

import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.event.TaskDeletedApplicationEvent;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.DeleteTaskContext;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteTaskCommandHandler {

    private final AuthenticationService authN;
    private final TaskAuthorizationService authZ;
    private final TaskWriteRepository repository;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public void handle(TaskId taskId) {
        Principal principal = authN.getCurrentPrincipal();
        authorizeRequest(principal, taskId);

        Task deletedTask = repository.deleteTask(taskId);

        TaskDeletedApplicationEvent event = prepareEvent(deletedTask);
        publishEvent(event);
    }

    private void publishEvent(TaskDeletedApplicationEvent event) {
        eventPublisher.publishEvent(event);
    }

    private TaskDeletedApplicationEvent prepareEvent(Task deletedTask) {
        return new TaskDeletedApplicationEvent(deletedTask);
    }

    private void authorizeRequest(Principal principal, TaskId taskId) {
        DeleteTaskContext context = DeleteTaskContext.createWithTaskId(taskId);
        authZ.authorizeDeleteTask(principal, context);
    }
}

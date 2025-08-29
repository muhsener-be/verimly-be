package app.verimly.task.application.usecase.command.task.delete;

import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.DeleteTaskContext;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteTaskCommandHandler {

    private final AuthenticationService authN;
    private final TaskAuthorizationService authZ;
    private final TaskWriteRepository repository;


    @Transactional
    public void handle(TaskId taskId) {
        Principal principal = authN.getCurrentPrincipal();
        authorizeRequest(principal, taskId);


        repository.deleteTask(taskId);
    }

    private void authorizeRequest(Principal principal, TaskId taskId) {
        DeleteTaskContext context = DeleteTaskContext.createWithTaskId(taskId);
        authZ.authorizeDeleteTask(principal, context);
    }
}

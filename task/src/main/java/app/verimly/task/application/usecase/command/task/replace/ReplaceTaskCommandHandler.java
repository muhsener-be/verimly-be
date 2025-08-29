package app.verimly.task.application.usecase.command.task.replace;

import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.exception.TaskNotFoundException;
import app.verimly.task.application.mapper.TaskAppMapper;
import app.verimly.task.application.ports.out.persistence.TaskReadRepository;
import app.verimly.task.application.ports.out.persistence.TaskSummaryProjection;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.ReplaceTaskContext;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReplaceTaskCommandHandler {

    private final TaskWriteRepository writeRepository;
    private final TaskReadRepository readRepository;
    private final AuthenticationService authenticationService;
    private final TaskAuthorizationService taskAuthorizationService;
    private final TaskAppMapper mapper;

    @Transactional
    public TaskSummaryData handle(ReplaceTaskCommand command) {

        Task taskToUpdate = fetchTaskAndCheckExistence(command.taskId());
        Principal principal = authenticationService.getCurrentPrincipal();
        authorizeRequest(principal, command);

        Task mergedTask = mergeTaskFromCommand(command, taskToUpdate);
        persistChanges(mergedTask);

        return fetchUpdatedValuesAndMapToResponse(mergedTask);


    }

    private TaskSummaryData fetchUpdatedValuesAndMapToResponse(Task task) {
        TaskSummaryProjection projection = readRepository.fetchTaskSummaryById(task.getId());
        return mapper.toTaskDetailsData(projection);
    }

    private void persistChanges(Task task) {
        writeRepository.update(task);
    }

    private Task mergeTaskFromCommand(ReplaceTaskCommand command, Task task) {
        mapper.mergeTaskFrom(command, task);
        return task;
    }

    private void authorizeRequest(Principal principal, ReplaceTaskCommand command) throws SecurityException {
        ReplaceTaskContext context = ReplaceTaskContext.createWithTaskId(command.taskId());
        taskAuthorizationService.authorizeReplaceTask(principal, context);
    }

    private Task fetchTaskAndCheckExistence(TaskId taskId) {
        return writeRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(taskId)
        );
    }

}

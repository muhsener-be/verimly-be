package app.verimly.task.application.usecase.command.task.create;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.event.TaskCreatedApplicationEvent;
import app.verimly.task.application.exception.FolderNotFoundException;
import app.verimly.task.application.mapper.TaskAppMapper;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.CreateTaskContext;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.input.TaskCreationDetails;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.service.TaskDomainService;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateTaskCommandHandler {
    private final AuthenticationService authN;
    private final TaskAuthorizationService authZ;
    private final FolderWriteRepository folderWriteRepository;
    private final TaskAppMapper mapper;
    private final TaskDomainService taskDomainService;
    private final TaskWriteRepository repository;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public TaskCreationResponse handle(CreateTaskCommand command) {
        assertInputIsNotNull(command);

        Principal principal = authN.getCurrentPrincipal();
        authorizeRequest(principal, command);
        Folder folderToAssign = fetchFolderAndCheckExistence(command.folderId());
        Task taskCreated = createTask(principal, folderToAssign, command);
        Task persistedTask = persistTask(taskCreated);
        TaskCreatedApplicationEvent event = prepareEvent(principal, persistedTask);
        publishEvent(event);
        return prepareResponse(persistedTask);
    }


    private void assertInputIsNotNull(CreateTaskCommand command) {
        Assert.notNull(command, "createTaskCommand cannot be null");
    }

    protected void authorizeRequest(Principal principal, CreateTaskCommand command) throws SecurityException {
        CreateTaskContext context = CreateTaskContext.createWithFolderId(command.folderId());
        authZ.authorizeCreateTask(principal, context);
    }

    private Folder fetchFolderAndCheckExistence(FolderId folderId) throws TaskDataAccessException, FolderNotFoundException {
        return folderWriteRepository.findById(folderId).orElseThrow(
                () -> new FolderNotFoundException(folderId)
        );
    }

    private Task createTask(Principal principal, Folder folder, CreateTaskCommand command) throws TaskDomainException {
        TaskCreationDetails details = mapper.toTaskCreationDetails(command, principal.getId());
        return taskDomainService.createTask(folder, details);
    }


    private Task persistTask(Task task) throws TaskDataAccessException {
        return repository.save(task);
    }

    protected TaskCreatedApplicationEvent prepareEvent(Principal actor, Task task) throws IllegalArgumentException {
        return new TaskCreatedApplicationEvent(actor, task);
    }

    private void publishEvent(TaskCreatedApplicationEvent event) {
        eventPublisher.publishEvent(event);
    }

    private TaskCreationResponse prepareResponse(Task task) {
        return mapper.toTaskCreationResponse(task);
    }


}

package app.verimly.task.application.usecase.command.task.move_to_folder;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.exception.FolderNotFoundException;
import app.verimly.task.application.exception.TaskNotFoundException;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.MoveToFolderContext;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.service.TaskDomainService;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MoveTaskToFolderCommandHandler {

    private final TaskWriteRepository taskRepository;
    private final FolderWriteRepository folderRepository;
    private final AuthenticationService taskAuthenticationService;
    private final TaskAuthorizationService authorizationService;
    private final TaskDomainService taskDomainService;

    @Transactional
    public void handle(MoveTaskToFolderCommand command) throws SecurityException, FolderNotFoundException, TaskNotFoundException {
        Assert.notNull(command, "command cannot be null in MoveTaskToFolderCommandHandler");
        Task task = fetchTaskAndCheckExistence(command.taskId());
        Folder folder = fetchFolderAndCheckExistence(command.folderId());

        Principal principal = taskAuthenticationService.getCurrentPrincipal();
        authorizeRequest(principal, command);

        Task movedTask = moveTaskToFolder(task, folder);

        persistChanges(movedTask);

    }

    protected Task fetchTaskAndCheckExistence(TaskId taskId) throws TaskNotFoundException {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(taskId)
        );
    }

    protected Folder fetchFolderAndCheckExistence(FolderId folderId) throws FolderNotFoundException {
        return folderRepository.findById(folderId).orElseThrow(
                () -> new FolderNotFoundException(folderId)
        );
    }

    private void authorizeRequest(Principal principal, MoveTaskToFolderCommand command) throws SecurityException {
        MoveToFolderContext context = MoveToFolderContext.createWithTaskIdAndNewFolderId(command.taskId(), command.folderId());
        authorizationService.authorizeMoveToFolder(principal, context);
    }

    private Task moveTaskToFolder(Task task, Folder folder) throws TaskDomainException {
        return taskDomainService.moveToFolder(task, folder);
    }

    private Task persistChanges(Task task) throws TaskDataAccessException {
        return taskRepository.update(task);
    }
}

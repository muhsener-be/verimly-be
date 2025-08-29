package app.verimly.task.domain.service;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.factory.TaskFactory;
import app.verimly.task.domain.input.TaskCreationDetails;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class TaskDomainService {

    private final TaskFactory factory;

    public Task createTask(@NotNull Folder folderToAssign, @NotNull TaskCreationDetails taskDetails) throws TaskDomainException {
        Assert.notNull(folderToAssign, "folderToAssign cannot be null");
        Assert.notNull(taskDetails, "taskDetails cannot be null");
        Assert.equals(folderToAssign.getId(), taskDetails.folderId(), "FolderIds do not match.");


        ensureFolderAndTaskHaveTheSameOwner(folderToAssign, taskDetails.ownerId());

        return createTask(taskDetails);

    }

    public void moveToFolder(@NotNull Task taskToMove, @NotNull Folder newFolder) throws TaskDomainException {
        Assert.notNull(taskToMove, "taskToMove cannot be null");
        Assert.notNull(newFolder, "newFolder cannot be null");

        ensureFolderAndTaskHaveTheSameOwner(newFolder, taskToMove.getOwnerId());
        taskToMove.moveToFolder(newFolder.getId());

    }


    private void ensureFolderAndTaskHaveTheSameOwner(Folder folder, UserId taskOwnerId) {
        UserId folderOwner = folder.getOwnerId();

        if (!Objects.equals(folderOwner, taskOwnerId))
            throw new TaskDomainException(Task.Errors.FOLDER_OWNER_NOT_MATCH);
    }

    private Task createTask(TaskCreationDetails taskDetails) {
        return factory.create(
                taskDetails.ownerId(),
                taskDetails.folderId(),
                taskDetails.name(),
                taskDetails.description(),
                taskDetails.dueDate(),
                taskDetails.priority()
        );
    }


}

package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MoveToFolderContext extends TaskAuthorizationContext {

    private final FolderId newFolderId;

    protected MoveToFolderContext(TaskId taskId, FolderId newFolderId) {
        super(taskId);
        this.newFolderId = newFolderId;
    }

    public static MoveToFolderContext createWithTaskIdAndNewFolderId(@NotNull TaskId taskId, @NotNull FolderId newFolderId) {
        Assert.notNull(taskId, "The ID of the task which be moved cannot be null.");
        Assert.notNull(newFolderId, "The ID of the new folder cannot be null");
        return new MoveToFolderContext(taskId, newFolderId);
    }


}

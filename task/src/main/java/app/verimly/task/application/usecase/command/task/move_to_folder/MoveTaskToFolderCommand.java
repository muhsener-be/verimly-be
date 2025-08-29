package app.verimly.task.application.usecase.command.task.move_to_folder;

import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;


public record MoveTaskToFolderCommand(TaskId taskId, FolderId folderId) {

}

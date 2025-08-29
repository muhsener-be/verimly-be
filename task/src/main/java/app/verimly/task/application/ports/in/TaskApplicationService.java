package app.verimly.task.application.ports.in;

import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.application.usecase.command.task.move_to_folder.MoveTaskToFolderCommand;
import app.verimly.task.domain.vo.folder.FolderId;

import java.util.List;

public interface TaskApplicationService {

    TaskCreationResponse create(CreateTaskCommand command);

    List<TaskSummaryData> findTasksByFolderId(FolderId folderId);

    void moveToFolder(MoveTaskToFolderCommand command);
}

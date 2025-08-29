package app.verimly.task.application;

import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.ports.in.TaskApplicationService;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommandHandler;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.application.usecase.command.task.move_to_folder.MoveTaskToFolderCommand;
import app.verimly.task.application.usecase.command.task.move_to_folder.MoveTaskToFolderCommandHandler;
import app.verimly.task.application.usecase.query.task.list_by_folder.ListTasksByFolderQueryHandler;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskApplicationServiceImpl implements TaskApplicationService {

    private final CreateTaskCommandHandler createTaskCommandHandler;
    private final ListTasksByFolderQueryHandler listTasksByFolderQueryHandler;
    private final MoveTaskToFolderCommandHandler moveTaskToFolderCommandHandler;

    @Override
    public TaskCreationResponse create(CreateTaskCommand command) {
        return createTaskCommandHandler.handle(command);
    }

    @Override
    public List<TaskSummaryData> findTasksByFolderId(FolderId folderId) {
        return listTasksByFolderQueryHandler.handle(folderId);
    }

    @Override
    public void moveToFolder(MoveTaskToFolderCommand command) {
        moveTaskToFolderCommandHandler.handle(command);
    }
}

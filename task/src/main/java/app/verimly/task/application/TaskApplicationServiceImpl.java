package app.verimly.task.application;

import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.dto.TaskWithSessionsData;
import app.verimly.task.application.ports.in.TaskApplicationService;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommandHandler;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.application.usecase.command.task.delete.DeleteTaskCommandHandler;
import app.verimly.task.application.usecase.command.task.move_to_folder.MoveTaskToFolderCommand;
import app.verimly.task.application.usecase.command.task.move_to_folder.MoveTaskToFolderCommandHandler;
import app.verimly.task.application.usecase.command.task.replace.ReplaceTaskCommand;
import app.verimly.task.application.usecase.command.task.replace.ReplaceTaskCommandHandler;
import app.verimly.task.application.usecase.query.task.fetch_with_sessions.FetchTaskWithSessionsQueryHandler;
import app.verimly.task.application.usecase.query.task.list_by_folder.ListTasksByFolderQueryHandler;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskApplicationServiceImpl implements TaskApplicationService {

    private final CreateTaskCommandHandler createTaskCommandHandler;
    private final ListTasksByFolderQueryHandler listTasksByFolderQueryHandler;
    private final MoveTaskToFolderCommandHandler moveTaskToFolderCommandHandler;
    private final ReplaceTaskCommandHandler replaceTaskCommandHandler;
    private final DeleteTaskCommandHandler deleteTaskCommandHandler;
    private final FetchTaskWithSessionsQueryHandler fetchTaskWithSessionsQueryHandler;

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

    @Override
    public TaskSummaryData replaceTask(ReplaceTaskCommand command) {
        return replaceTaskCommandHandler.handle(command);

    }

    @Override
    public void deleteTask(TaskId taskId) {
        deleteTaskCommandHandler.handle(taskId);
    }

    @Override
    public TaskWithSessionsData fetchTaskWithSessions(TaskId taskId) {
        return fetchTaskWithSessionsQueryHandler.handle(taskId);
    }
}

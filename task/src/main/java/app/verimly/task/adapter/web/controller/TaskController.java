package app.verimly.task.adapter.web.controller;

import app.verimly.task.adapter.web.docs.CreateTaskSpringDoc;
import app.verimly.task.adapter.web.docs.DeleteTaskSpringDoc;
import app.verimly.task.adapter.web.docs.FetchTaskWithSessionSpringDoc;
import app.verimly.task.adapter.web.docs.ListTasksByFolderSpringDoc;
import app.verimly.task.adapter.web.docs.MoveToFolderSpringDoc;
import app.verimly.task.adapter.web.docs.ReplaceTaskSpringDoc;
import app.verimly.task.adapter.web.dto.aggregate.TaskWithSessionsWebResponse;
import app.verimly.task.adapter.web.dto.request.CreateTaskWebRequest;
import app.verimly.task.adapter.web.dto.request.MoveTaskToFolderWebRequest;
import app.verimly.task.adapter.web.dto.request.ReplaceTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.TaskCreationWebResponse;
import app.verimly.task.adapter.web.dto.response.TaskSummaryWebResponse;
import app.verimly.task.adapter.web.mapper.SessionWebMapper;
import app.verimly.task.adapter.web.mapper.TaskWebMapper;
import app.verimly.task.adapter.web.mapper.TotalTimeAndSessionWebResponses;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.dto.TaskWithSessionsData;
import app.verimly.task.application.ports.in.TaskApplicationService;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.application.usecase.command.task.move_to_folder.MoveTaskToFolderCommand;
import app.verimly.task.application.usecase.command.task.replace.ReplaceTaskCommand;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task", description = "APIs for task management")
public class TaskController {

    private final TaskApplicationService applicationService;
    private final TaskWebMapper taskMapper;
    private final SessionWebMapper sessionMapper;


    @PostMapping
    @CreateTaskSpringDoc
    public TaskCreationWebResponse createTask(@Valid @RequestBody CreateTaskWebRequest request) {

        CreateTaskCommand command = taskMapper.toCreateTaskCommand(request);
        TaskCreationResponse response = applicationService.create(command);
        log.info("Task created successfully: [ID: {}, Name: {}, Description: {}, Priority: {}, OwnerId: {}, FolderId: {}]",
                response.id(), response.name(), response.description(), response.priority(), response.ownerId(), response.folderId());
        return taskMapper.toTaskCreationWebResponse(response);
    }

    @GetMapping
    @ListTasksByFolderSpringDoc
    public List<TaskSummaryWebResponse> listTasksByFolder(@RequestParam("folderId") UUID folderId) {
        List<TaskSummaryData> summaryData = applicationService.findTasksByFolderId(FolderId.of(folderId));

        return taskMapper.toTaskSummaryWebResponse(summaryData);
    }


    @PutMapping("/{taskId}/folder")
    @MoveToFolderSpringDoc
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void moveToFolder(@PathVariable("taskId") UUID taskId,
                             @RequestBody MoveTaskToFolderWebRequest request) {

        MoveTaskToFolderCommand command = taskMapper.toMoveTaskToFolderCommand(taskId, request);
        applicationService.moveToFolder(command);

    }


    @PutMapping("/{taskId}")
    @ReplaceTaskSpringDoc
    public TaskSummaryWebResponse replaceTask(@PathVariable("taskId") UUID taskId,
                                              @RequestBody ReplaceTaskWebRequest request) {

        ReplaceTaskCommand command = taskMapper.toReplaceTaskCommand(request, TaskId.of(taskId));
        TaskSummaryData summaryData = applicationService.replaceTask(command);

        return taskMapper.toTaskSummaryWebResponse(summaryData);

    }

    @DeleteMapping("/{taskId}")
    @DeleteTaskSpringDoc
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("taskId") UUID taskUUID) {
        applicationService.deleteTask(TaskId.of(taskUUID));
    }

    @GetMapping("/{taskId}")
    @FetchTaskWithSessionSpringDoc
    public TaskWithSessionsWebResponse fetchTaskWithSessions(@PathVariable("taskId") UUID taskId) {
        TaskId idTask = TaskId.of(taskId);
        TaskWithSessionsData data = applicationService.fetchTaskWithSessions(idTask);

        TotalTimeAndSessionWebResponses totalTimeAndSessionWebResponses = sessionMapper.toTotalTimeAndSessionWebResponses(data.getSessions());
        return taskMapper.toWebResponse(data, totalTimeAndSessionWebResponses);

    }


}

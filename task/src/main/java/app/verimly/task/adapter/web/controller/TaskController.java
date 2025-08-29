package app.verimly.task.adapter.web.controller;

import app.verimly.task.adapter.web.docs.CreateTaskSpringDoc;
import app.verimly.task.adapter.web.dto.request.CreateTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.TaskCreationWebResponse;
import app.verimly.task.adapter.web.dto.response.TaskSummaryWebResponse;
import app.verimly.task.adapter.web.mapper.TaskWebMapper;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.ports.in.TaskApplicationService;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.domain.vo.folder.FolderId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskApplicationService applicationService;
    private final TaskWebMapper mapper;


    @PostMapping
    @CreateTaskSpringDoc
    public TaskCreationWebResponse createTask(@Valid @RequestBody CreateTaskWebRequest request) {

        CreateTaskCommand command = mapper.toCreateTaskCommand(request);
        TaskCreationResponse response = applicationService.create(command);
        log.info("Task created successfully: [ID: {}, Name: {}, Description: {}, Priority: {}, OwnerId: {}, FolderId: {}]",
                response.id(), response.name(), response.description(), response.priority(), response.ownerId(), response.folderId());
        return mapper.toTaskCreationWebResponse(response);
    }

    @GetMapping
    public List<TaskSummaryWebResponse> listTasksByFolder(@RequestParam("folderId") UUID folderId) {
        List<TaskSummaryData> summaryData = applicationService.findTasksByFolderId(FolderId.of(folderId));

        return mapper.toTaskSummaryWebResponse(summaryData);
    }


}

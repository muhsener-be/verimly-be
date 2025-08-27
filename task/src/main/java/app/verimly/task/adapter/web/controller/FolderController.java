package app.verimly.task.adapter.web.controller;

import app.verimly.task.adapter.web.docs.CreateFolderSpringDocs;
import app.verimly.task.adapter.web.dto.request.CreateFolderWebRequest;
import app.verimly.task.adapter.web.dto.response.FolderCreationWebResponse;
import app.verimly.task.adapter.web.mapper.FolderWebMapper;
import app.verimly.task.application.ports.in.FolderApplicationService;
import app.verimly.task.application.usecase.command.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.create.FolderCreationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderWebMapper mapper;
    private final FolderApplicationService folderApplicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CreateFolderSpringDocs
    public FolderCreationWebResponse createFolder(@Valid @RequestBody CreateFolderWebRequest request) {
        CreateFolderCommand command = mapper.toCreateFolderCommand(request);


        FolderCreationResponse response = folderApplicationService.create(command);
        log.info("Folder created successfully. [Owner: {}, FolderId: {}, FolderName: {}]",
                response.ownerId(), response.id(), response.name());

        return mapper.toFolderCreationWebResponse(response);


    }
}

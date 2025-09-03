package app.verimly.task.adapter.web.controller;

import app.verimly.task.adapter.web.docs.CreateFolderSpringDocs;
import app.verimly.task.adapter.web.docs.ListFoldersSpringDoc;
import app.verimly.task.adapter.web.dto.request.CreateFolderWebRequest;
import app.verimly.task.adapter.web.dto.response.FolderCreationWebResponse;
import app.verimly.task.adapter.web.dto.response.FolderSummaryWebResponse;
import app.verimly.task.adapter.web.mapper.FolderWebMapper;
import app.verimly.task.application.dto.FolderSummaryData;
import app.verimly.task.application.ports.in.FolderApplicationService;
import app.verimly.task.application.usecase.command.folder.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.folder.create.FolderCreationResponse;
import app.verimly.task.logging.FolderLog;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/folders")
@RequiredArgsConstructor
@Tag(name = "Folder", description = "APIs for folder management")
public class FolderController {

    private final FolderWebMapper mapper;
    private final FolderApplicationService folderApplicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CreateFolderSpringDocs
    public FolderCreationWebResponse createFolder(@Valid @RequestBody CreateFolderWebRequest request) {
        CreateFolderCommand command = mapper.toCreateFolderCommand(request);


        FolderCreationResponse response = folderApplicationService.create(command);

        FolderLog.folderCreated(
                "user: " + response.ownerId().toString(),
                response.id(),
                response.name()
        );


        return mapper.toFolderCreationWebResponse(response);
    }


    @GetMapping
    @ListFoldersSpringDoc
    public List<FolderSummaryWebResponse> listFolders() {
        List<FolderSummaryData> response = folderApplicationService.listFolders();
        return mapper.toFolderSummaryWebResponse(response);
    }
}

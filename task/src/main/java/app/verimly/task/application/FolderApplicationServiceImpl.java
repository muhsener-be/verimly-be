package app.verimly.task.application;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.task.application.dto.FolderSummaryData;
import app.verimly.task.application.ports.in.FolderApplicationService;
import app.verimly.task.application.usecase.command.folder.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.folder.create.CreateFolderCommandHandler;
import app.verimly.task.application.usecase.command.folder.create.FolderCreationResponse;
import app.verimly.task.application.usecase.query.folder.list.ListFoldersQueryHandler;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderApplicationServiceImpl implements FolderApplicationService {

    private final CreateFolderCommandHandler createFolderCommandHandler;
    private final ListFoldersQueryHandler listFoldersQueryHandler;

    @Override
    public FolderCreationResponse create(@NotNull CreateFolderCommand command) {
        Assert.notNull(command, "command cannot be null to create folder");
        return createFolderCommandHandler.handle(command);
    }


    @Override
    public List<FolderSummaryData> listFolders() {
        return listFoldersQueryHandler.handle();
    }

}

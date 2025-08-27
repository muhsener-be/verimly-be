package app.verimly.task;

import app.verimly.task.application.dto.FolderSummaryData;
import app.verimly.task.application.ports.in.FolderApplicationService;
import app.verimly.task.application.usecase.command.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.create.CreateFolderCommandHandler;
import app.verimly.task.application.usecase.command.create.FolderCreationResponse;
import app.verimly.task.application.usecase.query.ListFoldersQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderApplicationServiceImpl implements FolderApplicationService {

    private final CreateFolderCommandHandler createFolderCommandHandler;
    private final ListFoldersQueryHandler listFoldersQueryHandler;

    @Override
    public FolderCreationResponse create(CreateFolderCommand command) {
        return createFolderCommandHandler.handle(command);
    }


    @Override
    public List<FolderSummaryData> listFolders() {
        return listFoldersQueryHandler.handle();
    }

}

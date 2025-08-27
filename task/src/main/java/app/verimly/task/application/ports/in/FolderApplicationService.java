package app.verimly.task.application.ports.in;

import app.verimly.task.application.dto.FolderSummaryData;
import app.verimly.task.application.usecase.command.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.create.FolderCreationResponse;

import java.util.List;

public interface FolderApplicationService {

    FolderCreationResponse create(CreateFolderCommand command);

    List<FolderSummaryData> listFolders();
}

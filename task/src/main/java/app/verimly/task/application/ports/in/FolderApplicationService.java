package app.verimly.task.application.ports.in;

import app.verimly.task.application.usecase.command.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.create.FolderCreationResponse;

public interface FolderApplicationService {

    FolderCreationResponse create(CreateFolderCommand command);
}

package app.verimly.task;

import app.verimly.task.application.ports.in.FolderApplicationService;
import app.verimly.task.application.usecase.command.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.create.CreateFolderCommandHandler;
import app.verimly.task.application.usecase.command.create.FolderCreationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderApplicationServiceImpl implements FolderApplicationService {

    private final CreateFolderCommandHandler createFolderCommandHandler;

    @Override
    public FolderCreationResponse create(CreateFolderCommand command) {
        return createFolderCommandHandler.handle(command);
    }
}

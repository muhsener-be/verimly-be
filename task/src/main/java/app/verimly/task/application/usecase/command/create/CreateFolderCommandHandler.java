package app.verimly.task.application.usecase.command.create;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateFolderCommandHandler {


    @Transactional
    public FolderCreationResponse handle(CreateFolderCommand command) {

        return null;
    }
}

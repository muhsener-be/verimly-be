package app.verimly.task.application;

import app.verimly.task.application.ports.in.messaging.CreatedUserDetails;
import app.verimly.task.application.ports.in.messaging.UserCreatedEventHandler;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.repository.FolderWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedEventHandlerImpl implements UserCreatedEventHandler {
    private final FolderWriteRepository repository;

    @Override
    @Transactional
    public void handle(CreatedUserDetails details) {
        log.info("Handling user created event in task application layer... User [ID: {}, Email: {}]", details.id(), details.email());
        Folder defaultFolder = Folder.defaultFor(details.id());
        Folder savedFolder = repository.save(defaultFolder);
        log.info("Default folder saved for User: [ID: {}, Email: {}], Folder: [ID: {}]", details.id(), details.email(), savedFolder.getId());

    }
}

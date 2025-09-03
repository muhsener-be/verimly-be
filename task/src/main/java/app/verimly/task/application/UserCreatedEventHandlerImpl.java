package app.verimly.task.application;

import app.verimly.task.application.ports.in.messaging.CreatedUserDetails;
import app.verimly.task.application.ports.in.messaging.UserCreatedEventHandler;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.logging.Actor;
import app.verimly.task.logging.FolderLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedEventHandlerImpl implements UserCreatedEventHandler {
    private final FolderWriteRepository repository;

    @Override
    @Transactional
    public void handle(CreatedUserDetails user) {

        Folder defaultFolder = Folder.defaultFor(user.id());
        Folder savedFolder = repository.save(defaultFolder);

        MDC.put("source", "user-registration-listener");
        FolderLog.folderCreated(
                Actor.system(),
                savedFolder.getOwnerId(),
                savedFolder.getId(),
                savedFolder.getName()
        );
        MDC.clear();
    }
}

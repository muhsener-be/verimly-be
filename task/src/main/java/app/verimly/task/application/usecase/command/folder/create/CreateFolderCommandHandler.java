package app.verimly.task.application.usecase.command.folder.create;

import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.event.FolderCreatedApplicationEvent;
import app.verimly.task.application.exception.FolderBusinessException;
import app.verimly.task.application.exception.FolderSystemException;
import app.verimly.task.application.mapper.FolderAppMapper;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.CreateFolderContext;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.exception.FolderDomainException;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateFolderCommandHandler {

    private final AuthenticationService authN;
    private final TaskAuthorizationService authZ;
    private final FolderWriteRepository repository;
    private final ApplicationEventPublisher eventPublisher;
    private final FolderAppMapper mapper;


    @Transactional
    public FolderCreationResponse handle(CreateFolderCommand command) {
        Principal principal = authN.getCurrentPrincipal();
        authorizeRequest(principal, command);

        Folder folder = createFolder(principal, command);
        Folder persistedFolder = persistFolder(folder);

        FolderCreatedApplicationEvent event = prepareEvent(principal, persistedFolder);
        publishEvent(event);

        return prepareResponse(persistedFolder);
    }

    private void authorizeRequest(Principal principal, CreateFolderCommand command) throws SecurityException {
        authZ.authorizeCreateFolder(principal, new CreateFolderContext());
    }

    protected Folder createFolder(Principal principal, CreateFolderCommand command) throws FolderDomainException {
        try {
            return Folder.createWithDescriptionAndLabelColor(principal.getId(), command.name(), command.description(), command.labelColor());

        } catch (FolderDomainException domainException) {
            throw new FolderBusinessException(domainException.getErrorMessage(), domainException.getMessage(), domainException);
        }

    }

    private Folder persistFolder(Folder folder) throws TaskDataAccessException {
        try {
            return repository.save(folder);

        } catch (TaskDataAccessException dataAccessException) {
            throw new FolderSystemException(dataAccessException.getMessage(), dataAccessException);
        }
    }

    protected FolderCreatedApplicationEvent prepareEvent(Principal principal, Folder folder) {
        return new FolderCreatedApplicationEvent(principal, folder);
    }

    private void publishEvent(FolderCreatedApplicationEvent event) {
        eventPublisher.publishEvent(event);
    }

    private FolderCreationResponse prepareResponse(Folder folder) {
        return mapper.toFolderCreationResponse(folder);
    }
}

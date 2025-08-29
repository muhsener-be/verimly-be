package app.verimly.task.application.usecase.query.folder.list;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.dto.FolderSummaryData;
import app.verimly.task.application.mapper.FolderAppMapper;
import app.verimly.task.application.ports.out.persistence.FolderReadRepository;
import app.verimly.task.application.ports.out.persistence.FolderSummaryProjection;
import app.verimly.task.application.ports.out.security.action.FolderActions;
import app.verimly.task.application.ports.out.security.TaskAuthenticationService;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.domain.repository.TaskDataAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListFoldersQueryHandler {

    private final TaskAuthenticationService authN;
    private final TaskAuthorizationService authZ;
    private final FolderReadRepository repository;
    private final FolderAppMapper mapper;


    public List<FolderSummaryData> handle() {
        Principal principal = authN.getCurrentPrincipal();
        authorizeRequest(principal);
        return fetchAndMapFolders(principal.getId());
    }

    private List<FolderSummaryData> fetchAndMapFolders(UserId id) throws TaskDataAccessException {
        List<FolderSummaryProjection> projections = repository.findSummariesByOwnerId(id);
        return mapper.toFolderSummaryData(projections);
    }

    protected void authorizeRequest(Principal principal) throws SecurityException {
        authZ.authorize(principal, FolderActions.LIST, null);
    }

}

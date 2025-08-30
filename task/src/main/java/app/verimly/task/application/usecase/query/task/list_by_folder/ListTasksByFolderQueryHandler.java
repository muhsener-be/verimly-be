package app.verimly.task.application.usecase.query.task.list_by_folder;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.mapper.TaskAppMapper;
import app.verimly.task.application.ports.out.persistence.TaskReadRepository;
import app.verimly.task.application.ports.out.persistence.TaskSummaryProjection;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.ListTasksByFolderContext;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListTasksByFolderQueryHandler {


    private final AuthenticationService authN;
    private final TaskAuthorizationService authZ;
    private final TaskReadRepository taskReadRepository;
    private final TaskAppMapper taskAppMapper;

    @Transactional
    public List<TaskSummaryData> handle(FolderId folderId) {
        ensureFolderIdIsValid(folderId);


        Principal principal = authN.getCurrentPrincipal();
        authorizeRequest(principal, folderId);

        return fetchTaskAndMapToResponse(principal.getId(), folderId);


    }

    private void authorizeRequest(Principal principal, FolderId folderId) throws SecurityException {
        ListTasksByFolderContext taskAuthorizationContext = ListTasksByFolderContext.createWithFolderId(folderId);
        authZ.authorizeListTasksByFolder(principal, taskAuthorizationContext);
    }

    private List<TaskSummaryData> fetchTaskAndMapToResponse(UserId id, FolderId folderId) {
        List<TaskSummaryProjection> projections = taskReadRepository.fetchTaskInFolderForUser(id, folderId);
        return prepareResponse(projections);
    }

    private List<TaskSummaryData> prepareResponse(List<TaskSummaryProjection> projections) {
        return taskAppMapper.toTaskDetailsData(projections);
    }

    private static void ensureFolderIdIsValid(FolderId folderId) {
        Assert.notNull(folderId, "folderId cannot be null in ListTasksByFolderQueryHandler");
    }


}

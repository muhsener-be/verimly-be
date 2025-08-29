package app.verimly.task.application.ports.out.persistence;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.vo.folder.FolderId;

import java.util.List;

public interface TaskReadRepository {

    List<TaskSummaryProjection> fetchTaskInFolderForUser(UserId ownerId, FolderId folderId);
}

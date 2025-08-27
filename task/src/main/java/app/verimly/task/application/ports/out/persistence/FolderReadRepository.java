package app.verimly.task.application.ports.out.persistence;

import app.verimly.commons.core.domain.vo.UserId;

import java.util.List;

public interface FolderReadRepository {

    List<FolderSummaryProjection> findSummariesByOwnerId(UserId ownerId);
}

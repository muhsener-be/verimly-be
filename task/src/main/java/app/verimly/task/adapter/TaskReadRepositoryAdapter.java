package app.verimly.task.adapter;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.jparepo.TaskJpaRepository;
import app.verimly.task.application.ports.out.persistence.TaskSummaryProjection;
import app.verimly.task.application.ports.out.persistence.TaskReadRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskReadRepositoryAdapter implements TaskReadRepository {
    private final TaskJpaRepository jpaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TaskSummaryProjection> fetchTaskInFolderForUser(UserId ownerId, FolderId folderId) {
        Assert.notNull(ownerId, "ownerId cannot be null.");
        Assert.notNull(folderId, "folderId cannot be null.");
        try {
            return jpaRepository.findDetailsProjectionsByOwnerIdAndFolderId(ownerId.getValue(), folderId.getValue());
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }

    }
}

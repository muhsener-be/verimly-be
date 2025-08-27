package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.jparepo.FolderJpaRepository;
import app.verimly.task.application.ports.out.persistence.FolderReadRepository;
import app.verimly.task.application.ports.out.persistence.FolderSummaryProjection;
import app.verimly.task.domain.repository.TaskDataAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FolderReadRepositoryAdapter implements FolderReadRepository {

    private final FolderJpaRepository jpaRepository;

    @Override
    @Transactional
    public List<FolderSummaryProjection> findSummariesByOwnerId(UserId ownerId) {
        Assert.notNull(ownerId, "OwnerId cannot be null to find summaries by owner id.");
        try {
            return jpaRepository.findSummariesByOwnerId(ownerId.getValue());
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }
}

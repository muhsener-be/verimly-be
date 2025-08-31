package app.verimly.task.adapter;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.jparepo.TaskJpaRepository;
import app.verimly.task.adapter.persistence.jparepo.TimeSessionJpaRepository;
import app.verimly.task.adapter.persistence.mapper.TaskDbMapper;
import app.verimly.task.application.dto.TaskWithSessionsData;
import app.verimly.task.application.ports.out.persistence.SessionSummaryProjection;
import app.verimly.task.application.ports.out.persistence.TaskReadRepository;
import app.verimly.task.application.ports.out.persistence.TaskSummaryProjection;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskReadRepositoryAdapter implements TaskReadRepository {


    private final TaskJpaRepository jpaRepository;
    private final TimeSessionJpaRepository sessionJpaRepository;
    private final TaskDbMapper taskDbMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TaskSummaryProjection> fetchTaskInFolderForUser(UserId ownerId, FolderId folderId) {
        Assert.notNull(ownerId, "ownerId cannot be null.");
        Assert.notNull(folderId, "folderId cannot be null.");
        try {
            return jpaRepository.findSummaryProjectionsByOwnerIdAndFolderId(ownerId.getValue(), folderId.getValue());
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }

    }

    @Override
    public TaskSummaryProjection fetchTaskSummaryById(TaskId id) {
        Assert.notNull(id, "TaskId cannot be null to fetch task summary by id");
        try {
            return jpaRepository.findSummaryById(id.getValue());
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Optional<TaskWithSessionsData> fetchTaskDetailsWithSessionsById(TaskId id) {
        Assert.notNull(id, "TaskId cannot be null to fetch task details by id");
        try {
            Optional<TaskSummaryProjection> taskSummary = jpaRepository.findSummaryProjectionById(id.getValue());
            if (taskSummary.isEmpty())
                return Optional.empty();

            TaskSummaryProjection taskProjection = taskSummary.get();
            List<SessionSummaryProjection> sessionProjections = sessionJpaRepository.findSummaryProjectionByTaskIdOrderByStartedAtDesc(taskProjection.getId());

            return Optional.of(taskDbMapper.toTaskWithSessionsData(taskProjection, sessionProjections));
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }
}

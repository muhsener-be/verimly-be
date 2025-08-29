package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.TaskEntity;
import app.verimly.task.adapter.persistence.jparepo.TaskJpaRepository;
import app.verimly.task.adapter.persistence.mapper.TaskDbMapper;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskWriteRepositoryAdapter implements TaskWriteRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final TaskDbMapper taskDbMapper;
    private final TaskJpaRepository jpaRepository;

    @Override
    @Transactional
    public Task save(Task task) throws TaskDataAccessException {
        Assert.notNull(task, "task cannot be null to save.");
        try {
            TaskEntity jpa = taskDbMapper.toJpaEntity(task);
            entityManager.persist(jpa);
            entityManager.flush();
            return task;
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<Task> findById(TaskId taskId) throws TaskDataAccessException {
        Assert.notNull(taskId, "TaskId cannot be null to find by id");
        try {
            return jpaRepository.findById(taskId.getValue())
                    .map(taskDbMapper::toDomainEntity);
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public List<Task> findByOwnerId(UserId ownerId) throws TaskDataAccessException {
        Assert.notNull(ownerId, "OwnerId cannot be nul to find tasks by owner id");
        try {
            return jpaRepository.findByOwnerId(ownerId.getValue())
                    .stream().map(taskDbMapper::toDomainEntity)
                    .toList();
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }


}

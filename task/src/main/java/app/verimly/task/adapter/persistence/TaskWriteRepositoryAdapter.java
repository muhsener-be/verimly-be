package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.adapter.persistence.aspect.EnableSoftDeleteFilter;
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
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskWriteRepositoryAdapter implements TaskWriteRepository {

    private final TaskJpaRepository taskJpaRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final TaskDbMapper taskDbMapper;
    private final TaskJpaRepository jpaRepository;

    @Override
    @Transactional
    @EnableSoftDeleteFilter
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
    @EnableSoftDeleteFilter
    public Optional<Task> findById(TaskId taskId) throws TaskDataAccessException {
        Assert.notNull(taskId, "TaskId cannot be null to find by id");
        try {
            return jpaRepository.findTaskById(taskId.getValue())
                    .map(taskDbMapper::toDomainEntity);
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    @EnableSoftDeleteFilter
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

    @Override
    @Transactional
    @EnableSoftDeleteFilter
    public Task update(Task task) {
        Assert.notNull(task, "Task cannot be null to update.");
        try {
            TaskEntity taskEntity = taskJpaRepository.findById(task.getId().getValue()).orElseThrow();

            taskDbMapper.mergeFromDomain(task, taskEntity);

            entityManager.flush();
            return task;

        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    @EnableSoftDeleteFilter
    public Task deleteTask(TaskId taskId) {
        Assert.notNull(taskId, "taskId cannot be null to delete task.");
        try {
            TaskEntity taskEntityToDelete = taskJpaRepository.findTaskById(taskId.getValue()).orElseThrow();

            taskJpaRepository.delete(taskEntityToDelete);
            return taskDbMapper.toDomainEntity(taskEntityToDelete);
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }

    }

    @Override
    @Transactional
    @EnableSoftDeleteFilter
    public Optional<UserId> findOwnerOf(TaskId taskId) {
        Assert.notNull(taskId, "TaskId cannot be null to find owner");
        try {

            TypedQuery<UUID> typedQuery = entityManager.createQuery("SELECT t.ownerId FROM TaskEntity t WHERE t.id = :id", UUID.class)
                    .setParameter("id", taskId.getValue());

            UUID result = typedQuery.getSingleResult();

            return Optional.ofNullable(UserId.of(result));


        } catch (NoResultException noResultException) {
            return Optional.empty();
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }


}

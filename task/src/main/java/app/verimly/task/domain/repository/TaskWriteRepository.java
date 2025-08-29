package app.verimly.task.domain.repository;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.vo.task.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskWriteRepository {

    Task save(Task task) throws TaskDataAccessException;

    Optional<Task> findById(TaskId taskId) throws TaskDataAccessException;

    List<Task> findByOwnerId(UserId ownerId) throws TaskDataAccessException;

    Task update(Task task);

    Task deleteTask(TaskId taskId);

    Optional<UserId> findOwnerOf(TaskId taskId);
}

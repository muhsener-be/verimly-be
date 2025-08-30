package app.verimly.task.domain.repository;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.vo.session.SessionStatus;
import app.verimly.task.domain.vo.task.TaskId;

import java.util.List;

public interface TimeSessionWriteRepository {

    TimeSession save(TimeSession session);

    List<TimeSession> findByOwnerIdAndStatus(UserId ownerId, SessionStatus status);

    List<TimeSession> deleteAllByTaskId(TaskId id);
}

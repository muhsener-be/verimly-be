package app.verimly.task.domain.repository;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.vo.session.SessionStatus;
import app.verimly.task.domain.vo.task.TaskId;

import java.util.List;
import java.util.Optional;

public interface TimeSessionWriteRepository {

    TimeSession save(TimeSession session);

    List<TimeSession> findByOwnerIdAndStatus(UserId ownerId, SessionStatus status);

    List<TimeSession> deleteAllByTaskId(TaskId id);

    Optional<UserId> findOwnerOf(SessionId sessionId);

    Optional<TimeSession> findById(SessionId sessionId);

    TimeSession update(TimeSession session);
}

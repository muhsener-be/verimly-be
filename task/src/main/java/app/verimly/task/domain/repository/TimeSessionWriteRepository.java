package app.verimly.task.domain.repository;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.vo.session.SessionStatus;

import java.util.List;

public interface TimeSessionWriteRepository {

    TimeSession save(TimeSession session);

    List<TimeSession> findByOwnerIdAndStatus(UserId ownerId, SessionStatus status);
}

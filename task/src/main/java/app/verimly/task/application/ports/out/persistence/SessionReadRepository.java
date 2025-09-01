package app.verimly.task.application.ports.out.persistence;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.domain.vo.session.SessionStatus;

import java.util.List;

public interface SessionReadRepository {

    List<SessionSummaryData> findSessionsByStatusForUser(UserId userId, SessionStatus... orStatus);
}

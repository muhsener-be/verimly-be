package app.verimly.task.application.usecase.command.session.start;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.vo.session.SessionName;
import app.verimly.task.domain.vo.session.SessionStatus;
import app.verimly.task.domain.vo.task.TaskId;

import java.time.Instant;

public record SessionStartResponse(SessionId id, UserId ownerId, TaskId taskId, SessionName name, Instant startedAt,
                                   SessionStatus status) {

}

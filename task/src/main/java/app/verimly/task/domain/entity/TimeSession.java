package app.verimly.task.domain.entity;

import app.verimly.commons.core.domain.entity.BaseEntity;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.exception.TimeSessionDomainException;
import app.verimly.task.domain.vo.session.SessionName;
import app.verimly.task.domain.vo.session.SessionStatus;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

@Getter
public class TimeSession extends BaseEntity<SessionId> {

    private UserId ownerId;
    private SessionName name;
    private TaskId taskId;
    private Instant startedAt;
    private Instant pausedAt;
    private Instant finishedAt;
    private Duration totalPause;
    private SessionStatus status;


    protected TimeSession(SessionId id, UserId ownerId, SessionName name, TaskId taskId, Instant startedAt, Instant pausedAt, Instant finishedAt, Duration totalPause, SessionStatus status) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.taskId = taskId;
        this.startedAt = startedAt;
        this.pausedAt = pausedAt;
        this.finishedAt = finishedAt;
        this.totalPause = totalPause;
        this.status = status;
    }


    public static TimeSession startForTask(TaskId taskId, SessionName name, UserId ownerId) {
        SessionId sessionId = SessionId.random();
        TimeSession session = new TimeSession(sessionId, ownerId, name, taskId, Instant.now(), null, null, Duration.ZERO, SessionStatus.RUNNING);
        session.checkInvariants();
        return session;
    }

    public static TimeSession reconstruct(SessionId id, UserId ownerId, SessionName name, TaskId taskId, Instant startedAt,
                                          Instant pausedAt, Instant finishedAt, Duration totalPause, SessionStatus status) {
        return new TimeSession(id, ownerId, name, taskId, startedAt, pausedAt, finishedAt, totalPause, status);
    }

    private void checkInvariants() {
        if (taskId == null)
            throw new TimeSessionDomainException(Errors.TASK_NOT_EXIST);

        if (name == null)
            throw new TimeSessionDomainException(Errors.NAME_NOT_EXIST);

        if (ownerId == null)
            throw new TimeSessionDomainException(Errors.OWNER_NOT_EXIST);
    }

    public void pause() {
        if (!isRunning())
            throw new TimeSessionDomainException(Errors.TO_PAUSE_WRONG_STATUS);

        this.pausedAt = Instant.now();
        this.status = SessionStatus.PAUSED;
    }

    public void resume() {
        if (isRunning())
            throw new TimeSessionDomainException(Errors.TO_RESUME_WRONG_STATUS);

        Instant now = Instant.now();
        if (isPaused()) {
            addLastBreakTimeToTotalPause(now);
        }

        if (isFinished())
            this.finishedAt = null;

        this.status = SessionStatus.RUNNING;
    }

    public void finish() {
        if (isFinished())
            throw new TimeSessionDomainException(Errors.TO_FINISH_WRONG_STATUS);

        Instant now = Instant.now();

        if (isPaused()) {
            addLastBreakTimeToTotalPause(now);
        }

        this.finishedAt = now;
        this.status = SessionStatus.FINISHED;

    }

    private void addLastBreakTimeToTotalPause(Instant now) {
        Duration lastBreakTime = Duration.between(this.pausedAt, now);
        this.totalPause = totalPause.plus(lastBreakTime);
    }


    public boolean isFinished() {
        return this.status == SessionStatus.FINISHED;
    }

    public boolean isRunning() {
        return this.status == SessionStatus.RUNNING;
    }

    public boolean isPaused() {
        return this.status == SessionStatus.PAUSED;
    }

    public static final class Errors {
        public static final ErrorMessage NAME_NOT_EXIST = ErrorMessage.of("session.name-not-exist", "Session must have a name.");
        public static final ErrorMessage TASK_NOT_EXIST = ErrorMessage.of("session.name-not-exist", "Session must have a taskId.");

        public static final ErrorMessage TO_PAUSE_WRONG_STATUS = ErrorMessage.of("session.to-pause-wrong-status", "To pause a session, it must be running.");
        public static final ErrorMessage TO_RESUME_WRONG_STATUS = ErrorMessage.of("session.to-resume-wrong-status", "To resume a session, it must be paused or finished.");
        public static final ErrorMessage TO_FINISH_WRONG_STATUS = ErrorMessage.of("session.to-finish-wrong-status", "To finish a session, it must not be already finished.");

        public static final ErrorMessage TASK_OWNER_NOT_MATCH = ErrorMessage.of("session.task-owner-not-match", "The owner of the task and session must be the same.");

        public static final ErrorMessage OWNER_NOT_EXIST = ErrorMessage.of("session.owner-not-exist", "TimeSession must have an owner.");
    }
}

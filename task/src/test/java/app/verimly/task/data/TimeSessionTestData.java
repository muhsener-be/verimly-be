package app.verimly.task.data;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.SessionEntity;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.input.SessionCreationDetails;
import app.verimly.task.domain.vo.session.SessionName;
import app.verimly.task.domain.vo.session.SessionStatus;
import app.verimly.task.domain.vo.task.TaskId;
import com.github.javafaker.Faker;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeSessionTestData {

    private static final Faker FAKER = new Faker();
    private static final TimeSessionTestData INSTANCE = new TimeSessionTestData();

    public static TimeSessionTestData getInstance() {
        return INSTANCE;
    }

    public SessionId id() {
        return SessionId.random();
    }

    public TaskId taskId() {
        return TaskId.random();
    }

    public UserId ownerId() {
        return UserId.random();
    }

    public SessionName name() {
        return SessionName.of(FAKER.team().name());
    }

    public SessionCreationDetails sessionCreationDetailsWithOwnerId(UserId ownerId) {
        return new SessionCreationDetails(name(), ownerId);
    }

    public SessionEntity sessionEntity() {
        return SessionEntity.builder()
                .id(id().getValue())
                .taskId(taskId().getValue())
                .ownerId(ownerId().getValue())
                .name(name().getValue())
                .status(SessionStatus.RUNNING)
                .startedAt(Instant.now())
                .finishedAt(null)
                .pausedAt(null)
                .totalPause(Duration.ZERO)
                .build();
    }

    public SessionEntity sessionEntityWithOwnerId(UUID ownerId) {
        return sessionEntity().toBuilder().ownerId(ownerId).build();
    }

    public SessionEntity sessionEntityWithOwnerIdAndTaskId(UUID ownerId, UUID taskId) {
        return sessionEntityWithOwnerId(ownerId).toBuilder().taskId(taskId).build();
    }

    public TimeSession session() {
        return TimeSession.startForTask(taskId(), name(), ownerId());
    }

    public StartSessionForTaskCommand startSessionCommand() {
        return new StartSessionForTaskCommand(name(), taskId());
    }

    public TimeSession sessionWithTaskIdAndOwnerId(TaskId taskId, UserId ownerId) {
        return TimeSession.startForTask(taskId, name(), ownerId);

    }

    public SessionStartResponse sessionStartResponse(SessionId id, UserId ownerId, TaskId taskId) {
        return new SessionStartResponse(id, ownerId, taskId, name(), Instant.now(), SessionStatus.RUNNING);
    }

    public SessionSummaryData sessionSummaryDataWithRandomStartedAt() {
        return new SessionSummaryData(id().getValue(),
                ownerId().getValue(),
                taskId().getValue(),
                name().getValue(),
                "FINISHED",
                FAKER.date().future(365, TimeUnit.DAYS).toInstant(),
                null,
                null,
                null
        );

    }

    public List<SessionSummaryData> sessionSummaryDataWithRandomStartedAt(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> this.sessionSummaryDataWithRandomStartedAt())
                .collect(Collectors.toList());
    }
}

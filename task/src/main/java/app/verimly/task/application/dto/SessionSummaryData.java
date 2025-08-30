package app.verimly.task.application.dto;

import app.verimly.task.domain.vo.session.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class SessionSummaryData {

    private UUID id;
    private UUID ownerId;
    private UUID taskId;
    private String name;
    @Setter
    private String status;
    private Instant startedAt;
    private Instant pausedAt;
    private Instant finishedAt;
    private Duration totalPause;


    public boolean isPaused() {
        return Objects.equals(this.status, SessionStatus.PAUSED.name());
    }

    public boolean isRunning() {
        return Objects.equals(this.status, SessionStatus.RUNNING.name());
    }

    public boolean isFinished() {
        return Objects.equals(this.status, SessionStatus.FINISHED.name());
    }

}

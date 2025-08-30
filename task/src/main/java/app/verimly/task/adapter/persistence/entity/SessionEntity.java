package app.verimly.task.adapter.persistence.entity;

import app.verimly.commons.core.adapter.persistence.BaseJpaEntity;
import app.verimly.task.domain.vo.session.SessionName;
import app.verimly.task.domain.vo.session.SessionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "sessions")
@Setter
@NoArgsConstructor
public class SessionEntity extends BaseJpaEntity<UUID> {

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(nullable = false, length = SessionName.MAX_LENGTH)
    private String name;

    @Column(name = "task_id", nullable = false)
    private UUID taskId;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;
    @Column(name = "paused_at")
    private Instant pausedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(name = "total_pause")
    private Duration totalPause;

    @Column(nullable = false)
    private SessionStatus status;

    @Builder(toBuilder = true)
    public SessionEntity(UUID id, UUID ownerId, String name, UUID taskId, Instant startedAt, Instant pausedAt, Instant finishedAt, Duration totalPause, SessionStatus status) {
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
}

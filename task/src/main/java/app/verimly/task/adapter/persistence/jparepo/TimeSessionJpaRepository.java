package app.verimly.task.adapter.persistence.jparepo;

import app.verimly.task.adapter.persistence.entity.SessionEntity;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.ports.out.persistence.SessionSummaryProjection;
import app.verimly.task.domain.vo.session.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimeSessionJpaRepository extends JpaRepository<SessionEntity, UUID> {


    Optional<SessionEntity> findSessionById(UUID id);

    List<SessionEntity> findByOwnerIdAndStatus(UUID ownerId, SessionStatus status);

    void deleteByTaskId(UUID taskId);

    List<SessionEntity> findByTaskId(UUID taskId);

    List<SessionSummaryProjection> findSummaryProjectionByTaskIdOrderByStartedAtDesc(UUID taskId);

    @Query("SELECT new app.verimly.task.application.dto.SessionSummaryData(" +
            "s.id, s.ownerId, s.taskId, s.name, s.status , s.startedAt, s.pausedAt , s.finishedAt , s.totalPause ) FROM SessionEntity s " +
            "WHERE s.ownerId = :ownerId " +
            "AND s.status in (:status)")
    List<SessionSummaryData> findSummaryDataByOwnerIdAndStatusIn(UUID ownerId, SessionStatus... status);


}

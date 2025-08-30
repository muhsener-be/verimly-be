package app.verimly.task.adapter.persistence.jparepo;

import app.verimly.task.adapter.persistence.entity.SessionEntity;
import app.verimly.task.domain.vo.session.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimeSessionJpaRepository extends JpaRepository<SessionEntity, UUID> {


    Optional<SessionEntity> findSessionById(UUID id);

    List<SessionEntity> findByOwnerIdAndStatus(UUID ownerId, SessionStatus status);
}

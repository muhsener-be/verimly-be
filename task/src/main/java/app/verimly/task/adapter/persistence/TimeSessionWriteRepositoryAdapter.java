package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.adapter.persistence.aspect.EnableSoftDeleteFilter;
import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.SessionEntity;
import app.verimly.task.adapter.persistence.jparepo.TimeSessionJpaRepository;
import app.verimly.task.adapter.persistence.mapper.SessionDbMapper;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.repository.TimeSessionWriteRepository;
import app.verimly.task.domain.vo.session.SessionStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeSessionWriteRepositoryAdapter implements TimeSessionWriteRepository {

    private final TimeSessionJpaRepository timeSessionJpaRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final SessionDbMapper sessionDbMapper;


    @Override
    @Transactional
    @EnableSoftDeleteFilter
    public TimeSession save(TimeSession session) {
        Assert.notNull(session, "TimeSession cannot be null persist into db.");
        Assert.notNull(session.getId(), "ID of the TimeSession cannot be null persist into db.");

        try {
            SessionEntity jpaEntity = sessionDbMapper.toJpaEntity(session);
            entityManager.persist(jpaEntity);
            entityManager.flush();
            return session;

        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }


    }

    @Override
    @Transactional
    @EnableSoftDeleteFilter
    public List<TimeSession> findByOwnerIdAndStatus(UserId ownerId, SessionStatus status) {
        Assert.notNull(ownerId, "OwnerId cannot be null to find sessions of the user by status");
        Assert.notNull(status, "Status cannot be null to find sessions of the user by status");
        try {
            return timeSessionJpaRepository.findByOwnerIdAndStatus(ownerId.getValue(), status).stream().map(sessionDbMapper::toDomainEntity).toList();
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }

    }
}

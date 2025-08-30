package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.task.adapter.persistence.entity.SessionEntity;
import app.verimly.task.adapter.persistence.mapper.SessionDbMapper;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.repository.TimeSessionWriteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TimeSessionWriteRepositoryAdapter implements TimeSessionWriteRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final SessionDbMapper sessionDbMapper;


    @Override
    @Transactional
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
}

package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.jparepo.TimeSessionJpaRepository;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.ports.out.persistence.SessionReadRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.vo.session.SessionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SessionReadRepositoryAdapter implements SessionReadRepository {

    private final TimeSessionJpaRepository sessionJpaRepository;


    @Override
    @Transactional(readOnly = true)
    public List<SessionSummaryData> findSessionsByStatusForUser(UserId userId, SessionStatus... orStatus) {
        Assert.notNull(userId, "UserId cannot be null");
        try {
            return sessionJpaRepository.findSummaryDataByOwnerIdAndStatusIn(userId.getValue(), orStatus);
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }
}

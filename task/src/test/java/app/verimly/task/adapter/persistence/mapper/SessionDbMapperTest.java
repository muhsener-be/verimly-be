package app.verimly.task.adapter.persistence.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.task.adapter.persistence.entity.SessionEntity;
import app.verimly.task.application.mapper.TaskVoMapperImpl;
import app.verimly.task.data.TimeSessionTestData;
import app.verimly.task.domain.entity.TimeSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SessionDbMapperImpl.class, CoreVoMapperImpl.class, TaskVoMapperImpl.class})
class SessionDbMapperTest {

    static TimeSessionTestData DATA = TimeSessionTestData.getInstance();

    @Autowired
    SessionDbMapper mapper;

    static TimeSession session;

    @BeforeEach
    public void setup() {
        session = DATA.session();
    }

    @Test
    void toJpaEntity_whenDomainIsNull_thenReturnNull() {
        session = null;
        SessionEntity actual = mapper.toJpaEntity(session);

        assertNull(actual);
    }


    @Test
    void toJpaEntity_whenDomainValid_thenMaps() {

        SessionEntity actual = mapper.toJpaEntity(session);

        assertNotNull(actual);
        assertEquals(session.getId().getValue(), actual.getId());
        assertEquals(session.getName().getValue(), actual.getName());
        assertEquals(session.getTaskId().getValue(), actual.getTaskId());
        assertEquals(session.getOwnerId().getValue(), actual.getOwnerId());
        assertEquals(session.getStartedAt(), actual.getStartedAt());
        assertEquals(session.getFinishedAt(), actual.getFinishedAt());
        assertEquals(session.getTotalPause(), actual.getTotalPause());
        assertEquals(session.getPausedAt(), actual.getPausedAt());
        assertEquals(session.getStatus(), actual.getStatus());
    }


    @Test
    void toDomainEntity_whenJpaNull_thenReturnsNull() {
        SessionEntity jpa = null;

        TimeSession actual = mapper.toDomainEntity(jpa);

        assertNull(actual);
    }

    @Test
    void toDomainEntity_whenValid_thenMapsToDomain() {
        SessionEntity jpa = mapper.toJpaEntity(session);

        TimeSession actual = mapper.toDomainEntity(jpa);

        assertNotNull(actual);
        assertEquals(session.getId(), actual.getId());
        assertEquals(session.getName(), actual.getName());
        assertEquals(session.getStatus(), actual.getStatus());
        assertEquals(session.getStartedAt(), actual.getStartedAt());
        assertEquals(session.getPausedAt(), actual.getPausedAt());
        assertEquals(session.getTotalPause(), actual.getTotalPause());
        assertEquals(session.getTaskId(), actual.getTaskId());
        assertEquals(session.getOwnerId(), actual.getOwnerId());
        assertEquals(session.getFinishedAt(), actual.getFinishedAt());
    }

}
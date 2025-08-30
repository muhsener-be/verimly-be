package app.verimly.task.adapter.persistence.entity;

import app.verimly.commons.core.utils.MyStringUtils;
import app.verimly.task.data.TimeSessionTestData;
import app.verimly.task.domain.vo.session.SessionName;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SessionEntityIntegrationTest extends AbstractIntegrationTest {

    static final TimeSessionTestData SESSION_TEST_DATA = TimeSessionTestData.getInstance();
    static EntitySetupTestService testService;


    static SessionEntity sessionEntity;
    static UserEntity userEntity;
    static TaskEntity taskEntity;
    static FolderEntity folderEntity;

    @Override
    protected void persistAndFlush() {
        entityManager.persist(sessionEntity);
        entityManager.flush();

    }


    @BeforeEach
    public void setup() {
        testService = new EntitySetupTestService(entityManager);
        TestEntitySetup setup = testService.setup();
        userEntity = setup.getUser();
        taskEntity = setup.getTask();
        folderEntity = setup.getFolder();
        sessionEntity = SESSION_TEST_DATA.sessionEntityWithOwnerIdAndTaskId(userEntity.getId(),taskEntity.getId());
    }

    public static Stream<SessionEntity> supplySessionEntitiesWithNullField() {
        sessionEntity = SESSION_TEST_DATA.sessionEntity();
        return Stream.of(
                sessionEntity.toBuilder().ownerId(null).build(),
                sessionEntity.toBuilder().taskId(null).build(),
                sessionEntity.toBuilder().startedAt(null).build(),
                sessionEntity.toBuilder().status(null).build()
        );
    }

    @ParameterizedTest
    @MethodSource("supplySessionEntitiesWithNullField")
    void persist_whenNotNullFieldsNull_thenThrowsConstraintViolationException(SessionEntity argEntity) {
        sessionEntity = argEntity;

        assertThrowsConstraintViolationException();
    }

    @Test
    void persist_whenNameIsTooLong_thenThrowsDataException() {
        String tooLongSessionName = MyStringUtils.generateString(SessionName.MAX_LENGTH + 1);
        sessionEntity = sessionEntity.toBuilder().name(tooLongSessionName).build();

        assertThrowsDataException();
    }

    @Test
    void persist_whenEntityIsValid_thenPersist() {

        persistAndFlush();

        SessionEntity foundEntity = entityManager.find(SessionEntity.class, sessionEntity.getId());
        assertNotNull(foundEntity);
        assertEquals(sessionEntity.getId(), foundEntity.getId());
        assertEquals(sessionEntity.getStartedAt(), foundEntity.getStartedAt());
        assertEquals(sessionEntity.getName(), foundEntity.getName());
        assertEquals(sessionEntity.getOwnerId(), foundEntity.getOwnerId());
        assertEquals(sessionEntity.getFinishedAt(), foundEntity.getFinishedAt());
        assertEquals(sessionEntity.getPausedAt(), foundEntity.getPausedAt());
        assertEquals(sessionEntity.getTotalPause(), foundEntity.getTotalPause());
        assertEquals(sessionEntity.getStatus(), foundEntity.getStatus());
        assertEquals(sessionEntity.getTaskId(), foundEntity.getTaskId());

    }


    @Test
    void persist_whenOwnerNotExists_thenThrowsConstraintViolationException() {
        UUID ownerId = UUID.randomUUID();
        sessionEntity = sessionEntity.toBuilder().ownerId(ownerId).build();

        assertThrowsConstraintViolationException();
    }

    @Test
    void persist_whenTaskNotExists_thenThrowsConstraintViolationException() {
        UUID taskId = UUID.randomUUID();
        sessionEntity = sessionEntity.toBuilder().taskId(taskId).build();

        assertThrowsConstraintViolationException();
    }
}

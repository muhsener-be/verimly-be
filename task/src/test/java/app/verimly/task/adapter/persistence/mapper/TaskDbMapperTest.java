package app.verimly.task.adapter.persistence.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.task.adapter.persistence.entity.TaskEntity;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TaskDbMapperImpl.class, CoreVoMapperImpl.class})
class TaskDbMapperTest {


    TaskEntity taskEntityWithNullFields;
    Task taskWithNullFields;


    @Autowired
    TaskDbMapper taskDbMapper;

    TaskTestData DATA = TaskTestData.getInstance();
    Task task;

    @BeforeEach
    public void setup() {
        task = DATA.task();
        taskEntityWithNullFields = DATA.taskEntityWithNullFields();
        taskWithNullFields = DATA.taskWithNullFields();

    }


    @Test
    void toJpaEntity_whenTaskIsNull_thenReturnsNull() {
        task = null;

        TaskEntity jpaEntity = taskDbMapper.toJpaEntity(task);

        assertNull(jpaEntity);

    }


    @Test
    void toJpaEntity_whenTaskIsValid_thenMapsToJpaEntity() {

        TaskEntity jpaEntity = taskDbMapper.toJpaEntity(task);

        assertNotNull(jpaEntity);
        assertEntityEquals(task, jpaEntity);
    }


    @Test
    void toJpaEntity_whenTaskNull_thenDoesNotThrowNullPointerException() {

        Executable actions = () -> taskDbMapper.toJpaEntity(taskWithNullFields);

        assertDoesNotThrow(actions);
    }

    @Test
    void toDomainEntity_whenInputIsNull_thenReturnsNull() {
        Task domainEntity = taskDbMapper.toDomainEntity(null);

        assertNull(domainEntity);

    }

    @Test
    void toDomainEntity_whenValidJpaEntity_thenMapsToDomain() {
        TaskEntity jpaEntity = taskDbMapper.toJpaEntity(task);

        Task domainEntity = taskDbMapper.toDomainEntity(jpaEntity);

        assertNotNull(domainEntity);
        assertEntityEquals(jpaEntity, domainEntity);
    }

    @Test
    void toDomainEntity_whenJpaEntityWithNullValues_thenDoesNotThrowNullPointerException() {
        taskEntityWithNullFields.setId(UUID.randomUUID());

        Executable actions = () -> taskDbMapper.toDomainEntity(taskEntityWithNullFields);

        assertDoesNotThrow(actions);
    }

    private static void assertEntityEquals(TaskEntity expected, Task actual) {
        assertEquals(expected.getId(), actual.getId().getValue());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getPriority(), actual.getPriority());
        assertEquals(expected.getName(), actual.getName().getValue());
        assertEquals(expected.getDescription(), actual.getDescription().getValue());
        assertEquals(expected.getDueDate(), actual.getDueDate().getValue());
        assertEquals(expected.getFolderId(), actual.getFolderId().getValue());
        assertEquals(expected.getOwnerId(), actual.getOwnerId().getValue());
    }

    private void assertEntityEquals(Task expected, TaskEntity actual) {
        assertEquals(expected.getId().getValue(), actual.getId());
        assertEquals(expected.getFolderId().getValue(), actual.getFolderId());
        assertEquals(expected.getName().getValue(), actual.getName());
        assertEquals(expected.getOwnerId().getValue(), actual.getOwnerId());
        assertEquals(expected.getDescription().getValue(), actual.getDescription());
        assertEquals(expected.getDueDate().getValue(), actual.getDueDate());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getPriority(), actual.getPriority());
    }
}
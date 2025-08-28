package app.verimly.task.adapter.persistence.entity;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.utils.MyStringUtils;
import app.verimly.task.adapter.persistence.jparepo.TaskJpaRepository;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskId;
import app.verimly.task.domain.vo.task.TaskName;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.defer-datasource-initialization=true"
})
class TaskEntityIntegrationTest {

    private static final int TASK_DATA_COUNT = 3;
    TaskEntityTestBuilder builder = new TaskEntityTestBuilder();
    FolderTestData FOLDER_DATA = FolderTestData.getInstance();

    UserId userId;
    UserEntity user;
    FolderId folderId;
    FolderEntity folder;


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TaskJpaRepository taskJpaRepository;

    TaskEntity taskEntity;

    List<TaskEntity> taskEntities;

    @BeforeEach
    public void setup() {
        userId = UserId.random();
        folderId = FolderId.random();

        folder = FOLDER_DATA.folderWithIdAndOwnerId(folderId, userId);
        user = FOLDER_DATA.userEntityWithId(userId);

        taskEntity = builder.withRandomId().withOwnerId(userId.getValue()).withFolderId(folderId.getValue()).build();

        entityManager.persist(user);
        entityManager.persist(folder);
        entityManager.flush();

        taskEntities = new ArrayList<>();
        setupTasksToQuery(userId, folderId);
    }

    private void setupTasksToQuery(UserId userId, FolderId folderId) {
        for (int i = 0; i < TASK_DATA_COUNT; i++) {
            TaskEntity taskEntityToSave = builder.withRandomId().withOwnerId(userId.getValue()).withFolderId(folderId.getValue()).build();
            taskEntities.add(taskEntityToSave);
            System.out.println("Task" + i +":  " +  taskEntityToSave.getId());
        }

        taskEntities.forEach(entity -> {
            entityManager.persist(entity);
            entityManager.flush();
        });
    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(entityManager);
        assertNotNull(taskJpaRepository);


    }


    @Test
    void persist_whenIdIsNull_thenThrowsIdentifierGenerationException() {
        taskEntity = builder.withId(null).build();
        assertThrowsException(IdentifierGenerationException.class);
    }


    @Test
    void persist_whenOwnerIsNull_thenThrowsConstraintViolationException() {
        System.out.println("Test is starting...");
        taskEntity = builder.withRandomId().withOwnerId(null).build();
        System.out.println("Task in test: " + taskEntity.getId());
        assertThrowsConstraintViolationException();
    }


    @Test
    void persist_whenFolderIdIsNull_thenThrowsConstraintViolationException() {
        taskEntity = builder.withRandomId().withFolderId(null).build();
        assertThrowsConstraintViolationException();
    }

    @Test
    void persist_whenNameIsNull_thenThrowsConstraintViolationException() {
        taskEntity = builder.withRandomId().withName(null).build();
        assertThrowsConstraintViolationException();
    }

    @Test
    void persist_whenNameIsTooLong_thenThrowsDataException() {
        taskEntity = builder.withRandomId().withName(MyStringUtils.generateString(TaskName.MAX_LENGTH + 1)).build();
        assertThrowsDataException();
    }

    @Test
    void persist_whenDescriptionIsTooLong_thenThrowsDataException() {
        taskEntity = builder.withRandomId().withDescription(MyStringUtils.generateString(TaskDescription.MAX_LENGTH + 1)).build();
        assertThrowsDataException();
    }

    @Test
    void persist_whenUserNotExist_thenThrowsConstraintViolationException() {
        UUID differentOwnerId = UUID.randomUUID();
        taskEntity = builder.withRandomId().withOwnerId(differentOwnerId).build();

        assertThrowsConstraintViolationException();
    }

    @Test
    void persist_whenFolderNotExist_thenThrowsConstraintViolationException() {
        taskEntity = builder.withRandomId().withFolderId(FolderId.random().getValue()).build();

        assertThrowsConstraintViolationException();
    }

    @Test
    void persist_whenValidEntity_thenPersistToDb() {
        taskEntity = builder.withRandomId().build();

        assertDoesNotThrowException();
        TaskEntity foundEntity = entityManager.find(TaskEntity.class, taskEntity.getId());

        assertTaskEntitiesEqual(taskEntity, foundEntity);
        assertNotNull(foundEntity.getCreatedAt());
        assertNotNull(foundEntity.getUpdatedAt());
    }
//
   @Test
   void findById_whenExist_thenReturnTaskEntity(){
       TaskEntity task = taskEntities.getFirst();
       UUID taskId = task.getId();

       Optional<TaskEntity> byId = taskJpaRepository.findById(taskId);
       assertTrue(byId.isPresent());
       TaskEntity actualTask = byId.get();
       assertTaskEntitiesEqual(task,actualTask);
   }

   @Test
   void findById_whenNotExist_thenReturnsEmptyOptional(){
       UUID randomTaskId = TaskId.random().getValue();

       Optional<TaskEntity> byId = taskJpaRepository.findById(randomTaskId);


       assertTrue(byId.isEmpty());

   }


   @Test
   void findByFolderId_whenNotExist_thenReturnsEmptyList(){
       UUID randomFolderId = FolderId.random().getValue();

       List<TaskEntity> tasksFound = taskJpaRepository.findByFolderId(randomFolderId);

       assertTrue(tasksFound.isEmpty());
   }

    @Test
    void findByOwnerId_whenNotExist_thenReturnsEmptyList(){
        UUID randomOwnerId = UserId.random().getValue();

        List<TaskEntity> tasksFound = taskJpaRepository.findByOwnerId(randomOwnerId);

        assertTrue(tasksFound.isEmpty());
    }

//
    @Test
    void findByFolderId_whenFound_thenReturnsAllTasks() {
        UUID existingFolderId = this.folderId.getValue();

        List<TaskEntity> foundTasks = taskJpaRepository.findByFolderId(existingFolderId);

        assertTaskEntitiesEqual(taskEntities , foundTasks);
    }

    @Test
    void findByOwnerId_whenFound_thenReturnsAllTasks() {
        UUID existingUserId = this.userId.getValue();

        List<TaskEntity> foundTasks = taskJpaRepository.findByOwnerId(existingUserId);

        assertTaskEntitiesEqual(taskEntities , foundTasks);

    }


    private void assertTaskEntitiesEqual(List<TaskEntity> expected, List<TaskEntity> actual) {
        int size = taskEntities.size();
        for (int i = 0; i < size; i++) {
            TaskEntity expectedTask = expected.get(i);
            TaskEntity actualTask = actual.get(i);
            assertTaskEntitiesEqual(expectedTask,actualTask);

        }
    }

    private void assertTaskEntitiesEqual(TaskEntity expected, TaskEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getOwnerId(), actual.getOwnerId());
        assertEquals(expected.getFolderId(), actual.getFolderId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDueDate(), actual.getDueDate());
        assertEquals(expected.getPriority(), actual.getPriority());
        assertEquals(expected.getStatus(), actual.getStatus());
    }


    private void assertDoesNotThrowException() {
        assertDoesNotThrow(getPersistAndFlushExecutable());
    }


    private void assertThrowsDataException() {
        assertThrowsException(DataException.class);
    }

    private void assertThrowsDataIntegrityViolationException(){
        assertThrowsException(DataIntegrityViolationException.class);
    }

    private void assertThrowsConstraintViolationException() {
        assertThrowsException(ConstraintViolationException.class);
    }


    private void assertThrowsException(Class<? extends Throwable> exClass) {
        assertThrows(exClass, getPersistAndFlushExecutable());
    }

    private Executable getPersistAndFlushExecutable() {
        return this::persistAndFlush;
    }

    private void persistAndFlush() {
        entityManager.persist(taskEntity);
        entityManager.flush();

    }
}
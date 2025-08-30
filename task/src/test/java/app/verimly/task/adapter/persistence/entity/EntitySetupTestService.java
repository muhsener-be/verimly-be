package app.verimly.task.adapter.persistence.entity;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.data.TimeSessionTestData;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
public class EntitySetupTestService {

    @PersistenceContext
    private final EntityManager entityManager;

    private static final TaskTestData TASK_DATA = TaskTestData.getInstance();
    public static final TimeSessionTestData SESSION_DATA = TimeSessionTestData.getInstance();
    public static final FolderTestData FOLDER_TEST_DATA = FolderTestData.getInstance();

    public TestEntitySetup setup() {
        UserId userId = UserId.random();
        UserEntity user = FOLDER_TEST_DATA.userEntityWithId(userId);
        FolderEntity folder = FOLDER_TEST_DATA.folderEntityWithUserId(userId);
        TaskEntity task = TASK_DATA.taskEntityWithOwnerIdAndFolderId(userId, FolderId.of(folder.getId()));
        entityManager.persist(user);
        entityManager.persist(folder);
        entityManager.persist(task);
        entityManager.flush();

        return new TestEntitySetup(user, task, folder);

    }
}

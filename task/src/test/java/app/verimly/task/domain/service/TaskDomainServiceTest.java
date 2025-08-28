package app.verimly.task.domain.service;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.factory.TaskFactory;
import app.verimly.task.domain.input.TaskCreationDetails;
import app.verimly.task.domain.vo.folder.FolderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TaskDomainServiceTest {


    FolderTestData folderTestData = FolderTestData.getInstance();
    TaskTestData taskTestData = TaskTestData.getInstance();

    UserId ownerId = UserId.random();
    Folder folder;
    TaskCreationDetails taskCreationDetails;
    Task task = taskTestData.task();

    @Mock
    TaskFactory taskFactory;

    @InjectMocks
    TaskDomainService domainService;

    @BeforeEach
    public void setup() {
        folder = folderTestData.folderWithOwner(ownerId);
        taskCreationDetails = taskTestData.creationDetailsWithOwnerIdAndFolderId(ownerId, folder.getId());

        lenient().when(taskFactory.create(any(), any(), any(), any(), any(), any())).thenReturn(task);
    }


    @Test
    void createTask_whenArgumentsIsValid_thenReturnsValidTask() {
        Task taskCreated = domainService.createTask(folder, taskCreationDetails);

        assertEquals(task ,taskCreated);
    }

    @Test
    void createTask_whenFolderIsNull_thenThrowsIllegalArgumentException() {
        folder = null;

        assertThrowsIllegalArgumentException(folder, taskCreationDetails);
    }

    @Test
    void createTask_whenTaskCreationDetailsIsNull_thenThrowsIllegalArgumentException() {
        taskCreationDetails = null;

        assertThrowsIllegalArgumentException(folder, taskCreationDetails);
    }


    @Test
    void createTask_whenFolderIdAndDetailsFolderIdNotMatch_thenThrowsIllegalArgumentException() {
        FolderId differentFolderId = FolderId.random();
        taskCreationDetails = taskCreationDetails.withFolderId(differentFolderId);

        assertThrowsIllegalArgumentException(folder, taskCreationDetails);
    }


    @Test
    void createTask_whenFolderOwnerAndTaskOwnerDoesNotMatch_thenThrowsTaskDomainException() {
        UserId differentOwnerId = UserId.random();
        taskCreationDetails = taskCreationDetails.withOwnerId(differentOwnerId);

        assertThrowsTaskDomainException(folder, taskCreationDetails);
    }

    private void assertThrowsTaskDomainException(Folder folder, TaskCreationDetails taskCreationDetails) {
        assertThrows(TaskDomainException.class,
                () -> domainService.createTask(folder, taskCreationDetails));
    }

    private void assertThrowsIllegalArgumentException(Folder folder, TaskCreationDetails taskCreationDetails) {
        assertThrows(IllegalArgumentException.class,
                () -> domainService.createTask(folder, taskCreationDetails));
    }


}
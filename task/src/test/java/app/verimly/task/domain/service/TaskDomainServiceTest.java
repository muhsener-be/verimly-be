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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TaskDomainServiceTest {


    static FolderTestData folderTestData = FolderTestData.getInstance();
    static TaskTestData taskTestData = TaskTestData.getInstance();

    UserId ownerId = UserId.random();
    static Folder folder;
    TaskCreationDetails taskCreationDetails;
    static Task task;

    @Mock
    TaskFactory taskFactory;

    @InjectMocks
    TaskDomainService domainService;

    @BeforeEach
    public void setup() {
        folder = folderTestData.folderWithOwner(ownerId);
        task = taskTestData.task();
        taskCreationDetails = taskTestData.creationDetailsWithOwnerIdAndFolderId(ownerId, folder.getId());

        lenient().when(taskFactory.create(any(), any(), any(), any(), any(), any())).thenReturn(task);
    }


    @Nested
    @DisplayName("Create Task")
    public class CreateTask {

        @Test
        void createTask_whenArgumentsIsValid_thenReturnsValidTask() {
            Task taskCreated = domainService.createTask(folder, taskCreationDetails);

            assertEquals(task, taskCreated);
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
    }


    @Nested
    @DisplayName("Move To Folder")
    public class MoveToFolder {

        @ParameterizedTest
        @MethodSource("supplyNullInputsForMoveToFolder")
        void moveToFolder_whenFolderIsNull_thenThrowsIllegalArgumentException(Task taskArgument, Folder folderArgument) {
            task = taskArgument;
            folder = folderArgument;
            assertThrowsIllegalArgumentException(getMoveToTaskExecutable());
        }

        @Test
        void moveToFolder_whenFolderOwnerAndTaskOwnerAreDifferent_thenThrowsTaskDomainException() {
            task = taskTestData.withOwnerId(UserId.random());
            folder = folderTestData.folderWithOwner(UserId.random());

            assertThrowsTaskDomainException(getMoveToTaskExecutable());
        }

        @Test
        void moveToFolder_whenValidArguments_thenChangesTaskFolderId() {
            UserId sameOwnerId = UserId.random();
            task = taskTestData.withOwnerId(sameOwnerId);
            folder = folderTestData.folderWithOwner(sameOwnerId);
            FolderId taskInitialFolderId = task.getFolderId();

            domainService.moveToFolder(task, folder);

            FolderId lastFolderId = task.getFolderId();
            assertEquals(folder.getId(), lastFolderId);
            assertNotEquals(taskInitialFolderId, lastFolderId);
        }

        private Executable getMoveToTaskExecutable() {
            return () -> domainService.moveToFolder(task, folder);
        }

        static List<Arguments> supplyNullInputsForMoveToFolder() {
            return List.of(
                    Arguments.of(null, folderTestData.folderWithFullFields()),
                    Arguments.of(taskTestData.task(), null),
                    Arguments.of(null, null)
            );

        }

    }

    private void assertThrowsTaskDomainException(Executable moveToTaskExecutable) {
        assertThrowException(TaskDomainException.class, moveToTaskExecutable);
    }


    private void assertThrowsTaskDomainException(Folder folder, TaskCreationDetails taskCreationDetails) {
        assertThrows(TaskDomainException.class,
                () -> domainService.createTask(folder, taskCreationDetails));
    }

    private void assertThrowsIllegalArgumentException(Folder folder, TaskCreationDetails taskCreationDetails) {
        assertThrows(IllegalArgumentException.class,
                () -> domainService.createTask(folder, taskCreationDetails));
    }

    private void assertThrowsIllegalArgumentException(Executable executable) {
        assertThrowException(IllegalArgumentException.class, executable);
    }

    private void assertThrowException(Class<? extends Throwable> exceptionClass, Executable executable) {
        assertThrows(exceptionClass, executable);
    }


}
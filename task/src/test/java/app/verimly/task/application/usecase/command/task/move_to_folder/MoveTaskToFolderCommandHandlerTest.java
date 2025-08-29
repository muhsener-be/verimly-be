package app.verimly.task.application.usecase.command.task.move_to_folder;

import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.AbstractUnitTest;
import app.verimly.task.application.exception.FolderNotFoundException;
import app.verimly.task.application.exception.TaskNotFoundException;
import app.verimly.task.application.ports.out.security.TaskAuthenticationService;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.MoveToFolderContext;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.exception.TaskDomainException;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.service.TaskDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoveTaskToFolderCommandHandlerTest extends AbstractUnitTest {

    @Mock
    private TaskWriteRepository taskRepository;
    @Mock
    private FolderWriteRepository folderRepository;
    @Mock
    private TaskAuthenticationService taskAuthenticationService;
    @Mock
    private TaskAuthorizationService authorizationService;
    @Mock
    private TaskDomainService taskDomainService;

    @InjectMocks
    private MoveTaskToFolderCommandHandler handler;

    // Test verileri
    private MoveTaskToFolderCommand command;
    private Principal principal;
    private Task task;
    private Folder folder;

    @BeforeEach
    void setup() {
        // --- Test için gerekli temel nesneler oluşturuluyor ---
        command = TASK_TEST_DATA.moveToFolderCommand();
        principal = AUTHENTICATED_PRINCIPAL;
        task = TASK_TEST_DATA.task();
        folder = FOLDER_TEST_DATA.folderWithFullFields();

        // --- Mock'lar için varsayılan davranışlar tanımlanıyor ---
        // lenient() kullanıyoruz, çünkü bu genel hazırlık blokundaki her bir mock
        // her test metodu tarafından kullanılmayabilir. Bu, Mockito'nun "UnnecessaryStubbingException"
        // hatası vermesini engeller ve testleri daha temiz tutar.
        lenient().when(taskAuthenticationService.getCurrentPrincipal()).thenReturn(principal);
        lenient().when(taskRepository.findById(command.taskId())).thenReturn(Optional.of(task));
        lenient().when(folderRepository.findById(command.folderId())).thenReturn(Optional.of(folder));
        lenient().doNothing().when(authorizationService).authorizeMoveToFolder(any(Principal.class), any(MoveToFolderContext.class));
        lenient().when(taskDomainService.moveToFolder(task, folder)).thenReturn(task);
        lenient().when(taskRepository.update(task)).thenReturn(task);
    }

    @Test
    void handle_whenValidCommand_thenTaskIsMovedAndUpdated() {
        // Arrange (Given)
        // @BeforeEach içinde tüm hazırlıklar yapıldı.

        // Act (When)
        handler.handle(command);

        // Then (Assert & Verify)
        // 1. Davranışın en önemli çıktısını doğrula: Repository'nin update metodu
        // doğru şekilde güncellenmiş bir Task nesnesiyle mi çağrıldı?
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).update(taskCaptor.capture());
        Task updatedTask = taskCaptor.getValue();


        // 2. Güvenlik kontrolünün doğru parametrelerle yapıldığını doğrula.
        ArgumentCaptor<MoveToFolderContext> contextCaptor = ArgumentCaptor.forClass(MoveToFolderContext.class);
        verify(authorizationService).authorizeMoveToFolder(eq(principal), contextCaptor.capture());

        assertEquals(command.taskId(), contextCaptor.getValue().getTaskId(), "Yetkilendirme doğru task ile yapılmalıdır.");
        assertEquals(command.folderId(), contextCaptor.getValue().getNewFolderId(), "Yetkilendirme doğru folder ile yapılmalıdır.");

        // 3. Diğer önemli etkileşimlerin gerçekleştiğini doğrula.
        verify(taskDomainService).moveToFolder(task, folder);
    }

    @Test
    void handle_whenTaskNotFound_thenThrowsTaskNotFoundException() {
        // Arrange (Given)
        when(taskRepository.findById(command.taskId())).thenReturn(Optional.empty());

        // Act & Assert (When & Then)
        assertThrows(TaskNotFoundException.class,
                () -> handler.handle(command),
                "Var olmayan bir task ID'si için TaskNotFoundException fırlatılmalıdır.");

        // Then (Verify)
        // Sadece findById çağrılmalı, başka hiçbir etkileşim olmamalı.
        verify(taskRepository).findById(command.taskId());
        verifyNoInteractions(folderRepository, taskAuthenticationService, authorizationService, taskDomainService);
    }

    @Test
    void handle_whenFolderNotFound_thenThrowsFolderNotFoundException() {
        // Arrange (Given)
        when(folderRepository.findById(command.folderId())).thenReturn(Optional.empty());

        // Act & Assert (When & Then)
        assertThrows(FolderNotFoundException.class,
                () -> handler.handle(command),
                "Var olmayan bir folder ID'si için FolderNotFoundException fırlatılmalıdır.");

        // Then (Verify)
        // Süreç folderRepository'den sonra durmalı.
        verify(taskRepository).findById(command.taskId());
        verify(folderRepository).findById(command.folderId());
        verifyNoInteractions(taskAuthenticationService, authorizationService, taskDomainService);
    }

    @Test
    void handle_whenAuthorizationFails_thenThrowsSecurityException() {
        // Arrange (Given)
        doThrow(AUTHENTICATION_REQUIRED_EXCEPTION).when(authorizationService).authorizeMoveToFolder(any(Principal.class), any(MoveToFolderContext.class));

        // Act & Assert (When & Then)
        assertThrows(SecurityException.class,
                () -> handler.handle(command),
                "Yetkilendirme başarısız olduğunda SecurityException fırlatılmalıdır.");

        // Then (Verify)
        // Süreç yetkilendirme adımında durmalı.
        verify(taskRepository).findById(command.taskId());
        verify(folderRepository).findById(command.folderId());
        verify(taskAuthenticationService).getCurrentPrincipal();
        verify(authorizationService).authorizeMoveToFolder(any(Principal.class), any(MoveToFolderContext.class));
        verifyNoInteractions(taskDomainService);
        verify(taskRepository, never()).update(any(Task.class)); // Update'in hiç çağrılmadığından emin ol.
    }

    @Test
    void handle_whenDomainServiceFails_thenThrowsTaskDomainException() {
        // Arrange (Given)
        doThrow(TaskDomainException.class).when(taskDomainService).moveToFolder(task, folder);

        // Act & Assert (When & Then)
        assertThrows(TaskDomainException.class,
                () -> handler.handle(command),
                "Domain servisinde bir sorun olduğunda TaskDomainException fırlatılmalıdır.");

        // Then (Verify)
        // Süreç domain servisi çağrısından sonra durmalı.
        verify(taskDomainService).moveToFolder(task, folder);
        verify(taskRepository, never()).update(any(Task.class)); // Domain hatası sonrası update çağrılmamalı.
    }

    @Test
    void handle_whenRepositoryUpdateFails_thenThrowsTaskDataAccessException() {
        // Arrange (Given)
        doThrow(TaskDataAccessException.class).when(taskRepository).update(task);

        // Act & Assert (When & Then)
        assertThrows(TaskDataAccessException.class,
                () -> handler.handle(command),
                "Repository update işlemi başarısız olduğunda TaskDataAccessException fırlatılmalıdır.");

        // Then (Verify)
        // Tüm adımlar denenmiş olmalı.
        verify(taskRepository).findById(command.taskId());
        verify(folderRepository).findById(command.folderId());
        verify(taskAuthenticationService).getCurrentPrincipal();
        verify(authorizationService).authorizeMoveToFolder(any(Principal.class), any(MoveToFolderContext.class));
        verify(taskDomainService).moveToFolder(task, folder);
        verify(taskRepository).update(task); // Hatanın kaynağı olan çağrı doğrulanmalı.
    }
}


//@ExtendWith(MockitoExtension.class)
//class MoveTaskToFolderCommandHandlerTest extends AbstractUnitTest {
//
//
//    @Mock
//    private TaskWriteRepository taskRepository;
//    @Mock
//    private FolderWriteRepository folderRepository;
//    @Mock
//    private TaskAuthenticationService taskAuthenticationService;
//    @Mock
//    private TaskAuthorizationService authorizationService;
//    @Mock
//    private TaskDomainService taskDomainService;
//
//    @InjectMocks
//    private MoveTaskToFolderCommandHandler handler;
//
//
//    MoveTaskToFolderCommand command;
//    Principal principal;
//    Task task;
//    Folder folder;
//
//    @BeforeEach
//    void setup() {
//        command = TASK_TEST_DATA.moveToFolderCommand();
//        principal = AUTHENTICATED_PRINCIPAL;
//        task = TASK_TEST_DATA.task();
//        folder = FOLDER_TEST_DATA.folderWithFullFields();
//
//        lenient().when(taskAuthenticationService.getCurrentPrincipal()).thenReturn(principal);
//        lenient().when(taskRepository.findById(command.taskId())).thenReturn(Optional.of(task));
//        lenient().when(folderRepository.findById(command.folderId())).thenReturn(Optional.of(folder));
//        lenient().doNothing().when(authorizationService).authorizeMoveToFolder(any(Principal.class), any(MoveToFolderContext.class));
//        lenient().when(taskDomainService.moveToFolder(task, folder)).thenReturn(task);
//        lenient().when(taskRepository.update(task)).thenReturn(task);
//    }
//
//    @Test
//    void handle_whenValidCommand_thenSuccess() {
//
//        handler.handle(command);
//
//        verifyAuthenticationService();
//        verifyTaskRepositoryFindById();
//        verifyFolderRepoFindById();
//        verifyAuthZ();
//        verifyDomainService();
//        verifyTaskRepositoryUpdate();
//    }
//
//    @Test
//    void handle_whenTaskNoExist_thenThrowsTaskNotFoundException() {
//        when(taskRepository.findById(command.taskId())).thenReturn(Optional.empty());
//
//        assertThrowsExceptions(TaskNotFoundException.class, getHandleExecutable());
//
//        verifyTaskRepositoryFindById();
//        verifyNoInteractions(authorizationService, taskAuthenticationService, taskDomainService, folderRepository);
//        verifyTaskRepoUpdateNoInteraction();
//
//    }
//
//    @Test
//    void handle_whenFolderNoExist_thenThrowsFolderNotFoundException() {
//        when(folderRepository.findById(command.folderId())).thenReturn(Optional.empty());
//
//        assertThrowsExceptions(FolderNotFoundException.class, getHandleExecutable());
//
//        verifyTaskRepositoryFindById();
//        verifyFolderRepoFindById();
//        verifyNoInteractions(authorizationService, taskAuthenticationService, taskDomainService);
//        verifyTaskRepoUpdateNoInteraction();
//
//    }
//
//
//    @Test
//    void handle_ProblemDuringAuthorizing_thenThrowsSecurityException() {
//        doThrow(AUTHENTICATION_REQUIRED_EXCEPTION).when(authorizationService).authorizeMoveToFolder(any(Principal.class), any(MoveToFolderContext.class));
//
//        assertThrowsExceptions(SecurityException.class, getHandleExecutable());
//
//        verifyTaskRepositoryFindById();
//        verifyFolderRepoFindById();
//        verifyAuthenticationService();
//        verifyAuthZ();
//        verifyNoInteractions(taskDomainService);
//        verifyTaskRepoUpdateNoInteraction();
//
//    }
//
//    @Test
//    void handle_whenProblemInDomainService_thenTaskDomainException() {
//        doThrow(TaskDomainException.class).when(taskDomainService).moveToFolder(task, folder);
//
//        assertThrowsExceptions(TaskDomainException.class, getHandleExecutable());
//
//        verifyTaskRepositoryFindById();
//        verifyFolderRepoFindById();
//        verifyAuthenticationService();
//        verifyAuthZ();
//        verifyDomainService();
//
//        verifyTaskRepoUpdateNoInteraction();
//    }
//
//
//    @Test
//    void handle_whenProblemUpdatingTask_thenTaskDataAccessException() {
//        doThrow(TaskDataAccessException.class).when(taskRepository).update(task);
//
//        assertThrowsExceptions(TaskDataAccessException.class, getHandleExecutable());
//
//        verifyTaskRepositoryFindById();
//        verifyFolderRepoFindById();
//        verifyAuthenticationService();
//        verifyAuthZ();
//        verifyDomainService();
//        verifyTaskRepositoryUpdate();
//    }
//
//
//    private Executable getHandleExecutable() {
//        return () -> handler.handle(command);
//    }
//
//    private void verifyTaskRepositoryUpdate() {
//        verify(taskRepository).update(task);
//    }
//
//    private void verifyTaskRepoUpdateNoInteraction() {
//        verify(taskRepository, times(0)).update(task);
//    }
//
//    private void verifyDomainService() {
//        verify(taskDomainService).moveToFolder(task, folder);
//    }
//
//    private void verifyAuthZ() {
//        ArgumentCaptor<Principal> argumentCaptor = ArgumentCaptor.forClass(Principal.class);
//        verify(authorizationService).authorizeMoveToFolder(argumentCaptor.capture(), any(MoveToFolderContext.class));
//        assertEquals(principal, argumentCaptor.getValue());
//    }
//
//
//    private void verifyFolderRepoFindById() {
//        verify(folderRepository).findById(command.folderId());
//    }
//
//    private void verifyTaskRepositoryFindById() {
//        verify(taskRepository).findById(command.taskId());
//    }
//
//    private void verifyAuthenticationService() {
//        verify(taskAuthenticationService).getCurrentPrincipal();
//    }
//}
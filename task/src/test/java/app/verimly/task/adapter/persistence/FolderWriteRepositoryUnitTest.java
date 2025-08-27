package app.verimly.task.adapter.persistence;

import app.verimly.task.adapter.persistence.entity.FolderEntity;
import app.verimly.task.adapter.persistence.jparepo.FolderJpaRepository;
import app.verimly.task.adapter.persistence.mapper.FolderDbMapper;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.vo.FolderId;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FolderWriteRepositoryUnitTest {

    FolderTestData DATA = FolderTestData.getInstance();
    Folder folder = DATA.folder();
    FolderEntity folderEntity;

    @Mock
    EntityManager entityManager;
    @Mock
    FolderDbMapper mapper;
    @Mock
    FolderJpaRepository jpaRepository;

    @InjectMocks
    FolderWriteRepositoryAdapter adapter;

    @BeforeEach
    public void setup() {
        folderEntity = Mockito.mock(FolderEntity.class);
        lenient().when(mapper.toJpaEntity(folder)).thenReturn(folderEntity);
        lenient().doNothing().when(entityManager).persist(folderEntity);
        lenient().doNothing().when(entityManager).flush();
    }

    @Test
    void save_whenValidFolder_thenReturnsFolder() {


        Folder persistedFolder = adapter.save(folder);

        assertEquals(folder, persistedFolder);
        verify(mapper).toJpaEntity(folder);
        verify(entityManager).persist(folderEntity);
        verify(entityManager).flush();
    }

    @Test
    void save_whenProblemDuringFlushing_thenThrowsTaskDataAccessException() {
        RuntimeException anException = new RuntimeException("An exception");
        doThrow(anException).when(entityManager).flush();

        Executable action = () -> adapter.save(folder);


        TaskDataAccessException exception = assertThrows(TaskDataAccessException.class, action);


        assertEquals(anException, exception.getCause());
        verify(mapper).toJpaEntity(folder);
        verify(entityManager).persist(folderEntity);
        verify(entityManager).flush();


    }

    @Test
    void save_whenFolderIsNull_thenThrowsIllegalArgumentException() {
        Executable action = () -> adapter.save(null);
        assertThrows(IllegalArgumentException.class, action);
    }


    @Test
    void findById_whenIdIsNull_thenThrowsIllegalArgumentException() {
        Executable action = () -> adapter.findById(null);
        assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    void findById_whenNotFound_thenReturnsEmptyOptional() {
        FolderId folderId = folder.getId();
        when(entityManager.find(FolderEntity.class, folderId.getValue())).thenReturn(null);

        Optional<Folder> byId = adapter.findById(folderId);

        assertTrue(byId.isEmpty());

    }

    @Test
    void findById_whenFound_thenReturnsOptionalPresent() {
        FolderId folderId = folder.getId();
        when(entityManager.find(FolderEntity.class, folderId.getValue())).thenReturn(folderEntity);
        when(mapper.toDomainEntity(folderEntity)).thenReturn(folder);

        Optional<Folder> byId = adapter.findById(folderId);
        assertTrue(byId.isPresent());
        Folder found = byId.get();
        assertEquals(folder, found);
    }

    @Test
    void findById_whenProblemDuringFetching_thenThrowsTaskDataAccessException() {
        FolderId folderId = folder.getId();
        doThrow(TaskDataAccessException.class).when(entityManager).find(FolderEntity.class, folderId.getValue());


        Executable action = () -> adapter.findById(folderId);

        assertThrows(TaskDataAccessException.class, action);


    }


}
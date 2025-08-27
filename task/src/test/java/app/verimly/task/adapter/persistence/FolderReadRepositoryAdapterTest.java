package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.jparepo.FolderJpaRepository;
import app.verimly.task.application.ports.out.persistence.FolderSummaryProjection;
import app.verimly.task.domain.repository.TaskDataAccessException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class FolderReadRepositoryAdapterTest {

    @Mock
    FolderJpaRepository jpaRepository;
    @InjectMocks
    FolderReadRepositoryAdapter adapter;
    List<FolderSummaryProjection> projectionsList;

    UserId ownerId;

    @BeforeEach
    void setup() {
        ownerId = UserId.random();
        projectionsList = new ArrayList<>();

        lenient().when(jpaRepository.findSummariesByOwnerId(ownerId.getValue())).thenReturn(projectionsList);
    }

    @Test
    void should_setup_is_ok() {
        Assertions.assertNotNull(adapter);
        Assertions.assertNotNull(jpaRepository);


    }

    @Test
    void findSummariesByOwnerId_whenOwnerIdIsNull_thenThrowsIllegalArgumentException() {
        ownerId = null;

        Executable executable = getFindSummariesByOwnerIdExecutable();

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void findSummariesByOwerId_whenValidOwnerId_thenReturnsListOfProjection() {
        List<FolderSummaryProjection> actual = adapter.findSummariesByOwnerId(ownerId);

        assertEquals(projectionsList, actual);
    }

    @Test
    void findSummariesByOwnerId_whenProblemDuringFetching_thenThrowsTaskDataAccessException() {
        RuntimeException anException = new RuntimeException("An exception");
        doThrow(anException).when(jpaRepository).findSummariesByOwnerId(ownerId.getValue());

        Executable executable = getFindSummariesByOwnerIdExecutable();

        assertThrows(TaskDataAccessException.class,executable);

    }

    private @NotNull Executable getFindSummariesByOwnerIdExecutable() {
        return () -> adapter.findSummariesByOwnerId(ownerId);
    }

}
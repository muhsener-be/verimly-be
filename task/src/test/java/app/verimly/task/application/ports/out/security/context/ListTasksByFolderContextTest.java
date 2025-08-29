package app.verimly.task.application.ports.out.security.context;

import app.verimly.task.application.AbstractUnitTest;
import app.verimly.task.domain.vo.folder.FolderId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListTasksByFolderContextTest extends AbstractUnitTest {

    FolderId folderId = FolderId.random();

    @Test
    void createWithFolderId_whenFolderIdIsNull_thenThrowsIllegalArgumentException() {
        folderId = null;

        assertThrowsExceptions(IllegalArgumentException.class, () -> ListTasksByFolderContext.createWithFolderId(folderId));

    }

    @Test
    void createWithFolderId_whenFolderIdValid_thenSuccess() {
        ListTasksByFolderContext actual = ListTasksByFolderContext.createWithFolderId(folderId);

        assertEquals(folderId,actual.getFolderId());
    }
}
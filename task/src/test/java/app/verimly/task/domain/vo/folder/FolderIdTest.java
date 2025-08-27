package app.verimly.task.domain.vo.folder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FolderIdTest {

    @Test
    void of_whenValueIsNull_thenReturnsNull(){
        FolderId actual = FolderId.of(null);

        assertNull(actual);
    }

    @Test
    void reconstruct_whenValue_IsNull_thenReturnsNull(){
        FolderId actual = FolderId.reconstruct(null);

        assertNull(actual);
    }

}
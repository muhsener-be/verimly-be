package app.verimly.task.application.event;

import app.verimly.commons.core.security.AnonymousPrincipal;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.entity.Folder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FolderCreatedApplicationEventTest {

    Principal principal = new AnonymousPrincipal();
    Folder folder = FolderTestData.getInstance().folderWithFullFields();


    @Test
    void build_whenPrincipalIsNull_thenThrowsIllegalArgumentException() {
        principal = null;

        Executable executable = () -> new FolderCreatedApplicationEvent(principal, folder);

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void build_whenFolderIsNull_thenThrowsIllegalArgumentException() {
        folder = null;

        Executable executable = () -> new FolderCreatedApplicationEvent(principal, folder);

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void build_whenArgumentsIsValid_thenReturnsValidEvent() {

        FolderCreatedApplicationEvent event = new FolderCreatedApplicationEvent(principal, folder);

        assertEquals(principal,event.actor());
        assertEquals(folder,event.folder());

    }

}
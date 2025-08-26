package app.verimly.task.domain.entity;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.exception.FolderDomainException;
import app.verimly.task.domain.vo.FolderName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class FolderTest {

    public static final FolderName VALID_FOLDER_NAME = FolderName.of("Valid Folder Name");
    public static final UserId OWNER_ID = UserId.random();

    @Test
    void create_whenValid_thenReturnsValidFolder() {


        Folder folder = Folder.create(VALID_FOLDER_NAME, OWNER_ID);

        assertNotNull(folder.getId());
        assertEquals(VALID_FOLDER_NAME, folder.getName());
        assertEquals(OWNER_ID, folder.getOwnerId());
    }

    @Test
    void create_whenNameIsNull_thenThrowsFolderDomainException() {


        Executable action = () -> Folder.create(null, OWNER_ID);

        FolderDomainException exception = assertThrows(FolderDomainException.class, action);
        assertEquals(Folder.Errors.NAME_NOT_EXIST, exception.getErrorMessage());
    }

    @Test
    void create_whenOwnerIsNull_thenThrowsFolderDomainException() {
        Executable action = () -> Folder.create(VALID_FOLDER_NAME, null);

        FolderDomainException exception = assertThrows(FolderDomainException.class, action);
        assertEquals(Folder.Errors.OWNER_NOT_EXIST, exception.getErrorMessage());
    }
}
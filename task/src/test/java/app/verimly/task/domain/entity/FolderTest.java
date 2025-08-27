package app.verimly.task.domain.entity;

import app.verimly.commons.core.domain.vo.Color;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.exception.FolderDomainException;
import app.verimly.task.domain.vo.folder.FolderDescription;
import app.verimly.task.domain.vo.folder.FolderName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class FolderTest {

    public static final FolderName VALID_FOLDER_NAME = FolderName.of("Valid Folder Name");
    public static final FolderDescription VALID_DESCRIPTION = FolderDescription.of("A valid folder description");
    public static final UserId OWNER_ID = UserId.random();
    public static final Color VALID_COLOR = Color.of("#FFFFFF");

    @Test
    void create_whenValid_thenReturnsValidFolder() {


        Folder folder = Folder.create(OWNER_ID, VALID_FOLDER_NAME);

        assertNotNull(folder.getId());
        assertEquals(VALID_FOLDER_NAME, folder.getName());
        assertEquals(OWNER_ID, folder.getOwnerId());
    }

    @Test
    void create_whenNameIsNull_thenThrowsFolderDomainException() {


        Executable action = () -> Folder.create(OWNER_ID, null);

        FolderDomainException exception = assertThrows(FolderDomainException.class, action);
        assertEquals(Folder.Errors.NAME_NOT_EXIST, exception.getErrorMessage());
    }

    @Test
    void create_whenOwnerIsNull_thenThrowsFolderDomainException() {
        Executable action = () -> Folder.create(null, VALID_FOLDER_NAME);

        FolderDomainException exception = assertThrows(FolderDomainException.class, action);
        assertEquals(Folder.Errors.OWNER_NOT_EXIST, exception.getErrorMessage());
    }

    @Test
    void createWithDescription_whenArgumentsIsValid_thenReturnsValidFolder() {

        Folder actual = Folder.createWithDescription(OWNER_ID, VALID_FOLDER_NAME, VALID_DESCRIPTION);

        assertEquals(OWNER_ID, actual.getOwnerId());
        assertEquals(VALID_FOLDER_NAME, actual.getName());
        assertEquals(VALID_DESCRIPTION, actual.getDescription());
    }

    @Test
    void createWithDescriptionAndLabelColor_whenArgumentsIsValid_thenReturnsValidFolder() {

        Folder actual = Folder.createWithDescriptionAndLabelColor(OWNER_ID, VALID_FOLDER_NAME, VALID_DESCRIPTION, VALID_COLOR);

        assertEquals(OWNER_ID, actual.getOwnerId());
        assertEquals(VALID_FOLDER_NAME, actual.getName());
        assertEquals(VALID_DESCRIPTION, actual.getDescription());
        assertEquals(VALID_COLOR, actual.getLabelColor());

    }

    @Test
    void reconstruct_whenAllNullArguments_doesNotThrowAnyException() {

        Executable action = () -> Folder.reconstruct(null, null, null, null, null);

        assertDoesNotThrow(action);

    }


}
package app.verimly.task.domain.vo.folder;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.utils.MyStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.*;

class FolderNameTest {

    public static final String ANORMAL_NAME = "       A     nor  mal          ";
    public static final String NORMAL_NAME = "A nor mal";
    public static final String TOO_LONG_NAME;

    static {
        TOO_LONG_NAME = MyStringUtils.generateString(FolderName.MAX_LENGTH + 1);
    }

    @Test
    void of_whenNullValue_thenReturnsNull() {
        FolderName folderName = FolderName.of(null);

        assertNull(folderName);
    }

    @Test
    void of_whenBlank_thenReturnsNull() {
        FolderName folderName = FolderName.of("        ");

        assertNull(folderName);
    }


    @Test
    void of_whenAnormalValue_thenNormalizes() {
        FolderName actual = FolderName.of(ANORMAL_NAME);

        assertEquals(NORMAL_NAME, actual.getValue());

    }


    @Test
    void of_whenTooLongValue_thenThrowsInvalidDomainObjectException() {
        Executable action = () -> FolderName.of(TOO_LONG_NAME);

        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class, action);
        ErrorMessage actualErrorMessage = exception.getErrorMessage();

        assertEquals(FolderName.Errors.LENGTH,actualErrorMessage);

    }

    @Test
    void reconstruct_whenTooLongName_doesNotCheckInvariants(){
        ThrowingSupplier<FolderName> action = () -> FolderName.reconstruct(TOO_LONG_NAME);

        assertDoesNotThrow(action);
    }

    @Test
    void reconstruct_whenNullValue_thenReturnNull(){
        FolderName folderName = FolderName.reconstruct(null);

        assertNull(folderName);
    }

}
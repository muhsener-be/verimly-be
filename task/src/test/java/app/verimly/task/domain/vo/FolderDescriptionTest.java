package app.verimly.task.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.utils.MyStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class FolderDescriptionTest {

    private String descriptionValue = "A valid description";

    @Test
    void of_whenValueIsNull_thenReturnsNull() {

        descriptionValue = null;

        FolderDescription actual = FolderDescription.of(descriptionValue);

        assertNull(actual);

    }

    @Test
    void of_whenValueIsBlank_thenReturnsNull() {

        descriptionValue = "    ";

        FolderDescription actual = FolderDescription.of(descriptionValue);

        assertNull(actual);

    }


    @Test
    void of_whenValueIsTooLong_thenThrowsInvalidDomainException() {
        descriptionValue = MyStringUtils.generateString(FolderDescription.MAX_LENGTH + 1);


        Executable executable = () -> FolderDescription.of(descriptionValue);

        InvalidDomainObjectException ex = assertThrows(InvalidDomainObjectException.class, executable);
        ErrorMessage expectedErrorMessage = FolderDescription.Errors.LENGTH;

        ErrorMessage actualErrorMessage = ex.getErrorMessage();

        assertEquals(expectedErrorMessage,actualErrorMessage);;
    }

}
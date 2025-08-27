package app.verimly.commons.core.domain.vo;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    private String validHex = "#FFF";
    private String invalidHex = "FFF";

    @Test
    void of_whenValueIsNullOrBlank_thenReturnsNull(){
        String nullValue = null;
        String blankValue = "   ";

        Color actualOne = Color.of(nullValue);
        Color actualTwo = Color.of(blankValue);

        assertNull(actualOne);
        assertNull(actualTwo);

    }


    @Test
    void of_whenValidHex_thenReturnsColorInstance(){
        Color actual = Color.of(validHex);

        assertEquals(validHex,actual.getValue());
    }

    @Test
    void of_whenInvalidHex_thenThrowsInvalidDomainObjectException(){
        Executable exec = () -> Color.of(invalidHex);

        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class, exec);
        assertEquals(Color.Errors.HEX_FORMAT,exception.getErrorMessage());
    }

}
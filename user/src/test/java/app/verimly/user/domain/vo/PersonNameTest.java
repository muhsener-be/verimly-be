package app.verimly.user.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.utils.MyStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class PersonNameTest {

    public static final String NULL_STRING = null;
    public static final String BLANK_STRING = "  ";
    public static final String LAST_NAME = "Tester";
    public static final String FIRST_NAME = "Tester";
    public static final String FULL_NAME = FIRST_NAME + " " + LAST_NAME;
    public static final String TOO_LONG_FIRST_NAME = MyStringUtils.generateString(51);
    public static final String TOO_LONG_LAST_NAME = MyStringUtils.generateString(51);

    public static final String ANORMAL_NAME_PART = "  A     normalized  ";
    public static final String NORMALIZED_NAME_PART = "A normalized";
    public static final ErrorMessage NAME_PART_ERROR_MESSAGE = PersonName.Errors.NAME_PART;


    @Test
    @DisplayName("Name parts are both null.")
    public void of_nullFirstNameAndNullLastName_returnsNull() {

        //Act
        PersonName personName = PersonName.of(NULL_STRING, NULL_STRING);

        //Assert
        assertNull(personName);
    }

    @Test
    @DisplayName("Name parts are both blank.")
    public void of_blankFirstNameAndBlankLastName_returnsNull() {
        //Act
        PersonName personName = PersonName.of(BLANK_STRING, BLANK_STRING);
        //Assert
        assertNull(personName);
    }


    @Test
    @DisplayName("Null first name, throws error.")
    public void of_nullFirstName_thenThrows() {
        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class, () -> PersonName.of(NULL_STRING, LAST_NAME));


        assertEquals(NAME_PART_ERROR_MESSAGE, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Blank first name, throws error.")
    public void of_blankFirstName_thenThrows() {
        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class, () -> PersonName.of(BLANK_STRING, LAST_NAME));


        assertEquals(NAME_PART_ERROR_MESSAGE, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Null last name, throws error.")
    public void of_nullLastName_thenThrows() {
        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class,
                () -> PersonName.of(FIRST_NAME, NULL_STRING));

        assertEquals(NAME_PART_ERROR_MESSAGE, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Blank last name, throws error.")
    public void of_blankLastName_thenThrows() {
        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class,
                () -> PersonName.of(FIRST_NAME, BLANK_STRING));

        assertEquals(NAME_PART_ERROR_MESSAGE, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Too long first name")
    public void of_tooLongFirstName_thenThrows() {
        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class, () -> PersonName.of(TOO_LONG_FIRST_NAME, LAST_NAME));


        assertEquals(NAME_PART_ERROR_MESSAGE, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Too long last name")
    public void of_tooLongLastName_thenThrows() {
        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class,
                () -> PersonName.of(FIRST_NAME, TOO_LONG_LAST_NAME));


        assertEquals(NAME_PART_ERROR_MESSAGE, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Normalizes anormal first name parts.")
    public void of_anormalFirstName_thenNormalizedFirstName() {

        PersonName personName = PersonName.of(ANORMAL_NAME_PART, LAST_NAME);
        String actual = personName.getFirstName();

        assertEquals(NORMALIZED_NAME_PART, actual);
        assertEquals(LAST_NAME, personName.getLastName());
    }

    @Test
    @DisplayName("Normalizes anormal last name parts.")
    public void of_anormalLastName_thenNormalizedLastName() {

        PersonName actual = PersonName.of(FIRST_NAME, ANORMAL_NAME_PART);


        assertEquals(FIRST_NAME, actual.getFirstName());
        assertEquals(NORMALIZED_NAME_PART, actual.getLastName());

    }

    @Test
    public void reconstruct_doesNotNormalizationNorValidation() {
        AtomicReference<PersonName> reference = new AtomicReference<>();
        assertDoesNotThrow(() -> reference.set(PersonName.reconstruct(ANORMAL_NAME_PART, TOO_LONG_LAST_NAME)));
        PersonName actual = reference.get();

        assertEquals(ANORMAL_NAME_PART, actual.getFirstName());
        assertEquals(TOO_LONG_LAST_NAME, actual.getLastName());


    }

    @Test
    public void toString_thenReturnsFullName() {

        PersonName actual = PersonName.of(FIRST_NAME, LAST_NAME);

        assertEquals(FULL_NAME, actual.toString());

    }


}

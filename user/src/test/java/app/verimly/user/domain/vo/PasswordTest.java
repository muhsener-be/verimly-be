package app.verimly.user.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.utils.MyStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    public static final String PASSWORD_WITH_WHITESPACE = "password with whitespace";
    public static final String VALID_RAW_PASSWORD = "validPassword";
    public static final ErrorMessage WHITE_SPACE_ERROR_MESSAGE = Password.Errors.WHITE_SPACE;
    private static final ErrorMessage LENGTH_ERROR_MESSAGE = Password.Errors.LENGTH;

    private static final String TOO_SHORT_PASSWORD = MyStringUtils.generateString(Password.MIN_LENGTH - 1);
    private static final String TOO_LONG_PASSWORD = MyStringUtils.generateString(Password.MAX_LENGTH + 1);

    public static final Password VALID_PASSWORD = Password.withRaw(VALID_RAW_PASSWORD);
    public static final String VALID_ENCRYPTED = "aklsdjalksdjalksdj";
    public static final String EMPTY_STRING = "";


    @Test
    @DisplayName("If raw is null, then return null.")
    public void withRaw_nullRawValue_thenReturnNull() {

        Password actual = Password.withRaw(null);

        assertNull(actual);

    }

    @Test
    @DisplayName("includes white-space, throws exception.")
    public void withRaw_includingWhiteSpace_thenThrows() {
        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class,
                () -> Password.withRaw(PASSWORD_WITH_WHITESPACE));

        ErrorMessage actualErrorMessage = exception.getErrorMessage();
        assertEquals(WHITE_SPACE_ERROR_MESSAGE, actualErrorMessage);
    }

    @Test
    @DisplayName("Too short password, throws exception.")
    public void withRaw_tooShortPassword_thenThrows() {
        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class,
                () -> Password.withRaw(TOO_SHORT_PASSWORD));

        ErrorMessage actualErrorMessage = exception.getErrorMessage();
        assertEquals(LENGTH_ERROR_MESSAGE, actualErrorMessage);
    }

    @Test
    @DisplayName("Too long password, throws exception.")
    public void withRaw_tooLongPassword_thenThrows() {
        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class,
                () -> Password.withRaw(TOO_LONG_PASSWORD));

        ErrorMessage actualErrorMessage = exception.getErrorMessage();
        assertEquals(LENGTH_ERROR_MESSAGE, actualErrorMessage);
    }

    @Test
    @DisplayName("If encrypted password is null or blank, throws illegal argument exception.")
    public void encrypt_nullEncryptedPassword_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> VALID_PASSWORD.encrypt(null));

    }

    @Test
    @DisplayName("When encrypt password, then raw is made empty.")
    public void encrypt_makesRawPasswordEmptyString() {
        VALID_PASSWORD.encrypt(VALID_ENCRYPTED);

        assertEquals(VALID_ENCRYPTED, VALID_PASSWORD.getEncrypted());
        assertEquals(EMPTY_STRING, VALID_PASSWORD.getRaw());
    }

    @Test
    @DisplayName("isEncrypted: returns false")
    public void isEncrypted_whenNotEncrypted_thenReturnFalse() {
        boolean isEncrypted = VALID_PASSWORD.isEncrypted();
        assertFalse(isEncrypted);
    }

    @Test
    @DisplayName("isEncrypted: return true ")
    public void isEncrypted_whenEncrypted_thenReturnTrue() {
        VALID_PASSWORD.encrypt(VALID_ENCRYPTED);

        boolean isEncrypted = VALID_PASSWORD.isEncrypted();
        assertTrue(isEncrypted);
    }


    @Test
    @DisplayName("Reconstruct: Does not check invariants")
    public void reconstruct_doesNotCheckInvariants() {
        Password actual = Password.reconstruct(null);
        assertNotNull(actual);
    }


}

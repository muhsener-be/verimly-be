package app.verimly.commons.core.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    public static final String EMAIL_VALUE_WITH_WHITE_SPACES = "  test@test.com  ";
    public static final String INVALID_EMAIL_VALUE_WITH_WHITE_SPACES = "  testtest.com  ";
    public static final String INVALID_EMAIL_VALUE = "invalidemail.com";
    public static final ErrorMessage EMAIL_FORMAT_ERROR = Email.Errors.FORMAT;

    @Test
    public void of_whenValueIsNull_thenReturnNull() {
        String nullValue = null;

        Email actual = Email.of(nullValue);

        assertNull(actual);

    }

    @Test
    public void of_whenValueHasLeadingOrTrailingWhiteSpaces_thenTrims() {
        Email actual = Email.of(EMAIL_VALUE_WITH_WHITE_SPACES);
        String expected = EMAIL_VALUE_WITH_WHITE_SPACES.trim();

        assertEquals(expected, actual.getValue());
    }

    @Test
    public void reconstruct_whenInvalidEmail_thenDoesNothing() {

        AtomicReference<Email> actual = new AtomicReference<>();
        assertDoesNotThrow(() -> actual.set(Email.reconstruct(INVALID_EMAIL_VALUE_WITH_WHITE_SPACES)));

        assertEquals(INVALID_EMAIL_VALUE_WITH_WHITE_SPACES, actual.get().getValue());

    }


    @ParameterizedTest
    @MethodSource("supplyInvalidEmails")
    public void of_invalidFormat_thenThrowsInvalidDomainObjectException(String invalidEmailValue) {

        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class,
                () -> Email.of(invalidEmailValue));

        ErrorMessage actual = exception.getErrorMessage();
        assertEquals(EMAIL_FORMAT_ERROR.code(), actual.code());
        assertEquals(EMAIL_FORMAT_ERROR.defaultMessage(), actual.defaultMessage());
    }

    static Stream<String> supplyInvalidEmails() {
        return Stream.of(
            INVALID_EMAIL_VALUE,
            "",
            "plainaddress",
            "@missingusername.com",
            "username@.com",
            "username@com",
            "username@domain..com",
            "username@domain,com",
            "username@domain@domain.com",
            "username@domain .com",
            "username@-domain.com",
            "username@domain.com (Joe Smith)",
            "username@domain..com",
            "username@.domain.com",
            "username@domain.com.",
            "username@.com.com",
            "username@domain..com"
        );
    }


}

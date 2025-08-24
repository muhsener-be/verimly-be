package app.verimly.commons.core.domain.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EmailTest {

    public static final String EMAIL_VALUE_WITH_WHITE_SPACES = "  test@test.com  ";

    @Test
    public void of_whenValueIsNull_thenReturnNull() {
        String nullValue = null;

        Email actual = Email.of(null);

        assertNull(actual);

    }

    @Test
    public void of_whenValueHasLeadingOrTrailingWhiteSpaces_thenTrims() {


        Email actual = Email.of(EMAIL_VALUE_WITH_WHITE_SPACES);
        String expected = EMAIL_VALUE_WITH_WHITE_SPACES.trim();

        assertEquals(expected, actual.getValue());
    }

    @Test
    public void reconstruct_whenValueHasLeadingOrTrailingWhiteSpaces_thenDoesNothing() {
        Email actual = Email.reconstruct(EMAIL_VALUE_WITH_WHITE_SPACES);

        assertEquals(EMAIL_VALUE_WITH_WHITE_SPACES, actual.getValue());

    }

}

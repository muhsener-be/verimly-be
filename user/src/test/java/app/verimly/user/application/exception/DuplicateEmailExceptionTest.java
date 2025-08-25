package app.verimly.user.application.exception;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.user.data.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ThrowableNotThrown")
public class DuplicateEmailExceptionTest {

    Email email = UserTestData.getInstance().email();

    @Test
    public void construct_happy_path() {
        // ACT
        DuplicateEmailException exception = new DuplicateEmailException(email);


        // ASSERT
        assertEquals(email, exception.getEmail());
        String expectedMessage = DuplicateEmailException.MESSAGE_TEMPLATE.formatted(email.getValue());
        assertEquals(expectedMessage, exception.getMessage());
        assertNotNull(exception.getErrorMessage());

    }

    @Test
    public void construct_whenEmailIsNull_thenThrows() {
        // ARRANGE
        email = null;

        // ACT
        Executable action = () -> new DuplicateEmailException(email);

        // ASSERT
        assertThrows(IllegalArgumentException.class,
                action);
    }
}

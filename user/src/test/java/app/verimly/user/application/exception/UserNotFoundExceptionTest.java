package app.verimly.user.application.exception;

import app.verimly.commons.core.domain.vo.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserNotFoundExceptionTest {

    static UserId userId;

    @BeforeEach
    public void setup() {
        userId = UserId.random();
    }

    @Test
    void construct_whenUserIdNull_thenThrowsIllegalArgumentException() {
        userId = null;

        assertThrows(IllegalArgumentException.class, getConstructExecutable());
    }

    @Test
    void construct_whenValidUserId_shouldConstructValidException() {

        UserNotFoundException ex = new UserNotFoundException(userId);

        assertEquals(UserNotFoundException.RESOURCE_NAME , ex.getResourceType());
        assertEquals(userId.toString() , ex.getResourceId());


    }


    private Executable getConstructExecutable() {
        return () -> new UserNotFoundException(userId);
    }
}
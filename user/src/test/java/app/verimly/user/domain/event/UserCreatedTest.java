package app.verimly.user.domain.event;

import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserCreatedTest {
    UserTestData userTestData = UserTestData.getInstance();
    User user = userTestData.user();

    @Test
    public void of_happyPath() {
        UserCreated event = UserCreated.of(user);
        assertEquals(user, event.getUser());
    }

    @Test
    public void of_nullUser_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> UserCreated.of(null));
    }
}

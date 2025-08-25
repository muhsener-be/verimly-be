package app.verimly.user.application.event;

import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserCreatedApplicationEventTest {

    private User user = UserTestData.getInstance().user();

    @Test
    void of_happy_path() {
        UserCreatedApplicationEvent event = UserCreatedApplicationEvent.of(user);
        assertEquals(user, event.getUser());
        assertNotNull(event.getOccurredAt());
    }
}

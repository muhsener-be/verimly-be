package app.verimly.user.application.usecase.command.create;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.vo.Password;
import app.verimly.user.domain.vo.PersonName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCreationResponseTest {

    UserTestData DATA = UserTestData.getInstance();
    UserId userId = DATA.userId();
    PersonName personName = DATA.personName();
    Email email = DATA.email();
    Password password = DATA.password().raw();

    @Test
    public void of_happy_path() {
        UserCreationResponse actual = UserCreationResponse.of(userId, personName, email);

        assertEquals(userId, actual.id());
        assertEquals(personName, actual.name());
        assertEquals(email, actual.email());

    }
}

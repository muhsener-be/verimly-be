package app.verimly.user.application.usecase.command.create;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.vo.Password;
import app.verimly.user.domain.vo.PersonName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserCommandTest {

    UserTestData DATA = UserTestData.getInstance();
    PersonName personName = DATA.personName();
    Email email = DATA.email();
    Password password = DATA.password().raw();

    @Test
    public void of_happyPath() {
        CreateUserCommand actual = CreateUserCommand.of(personName, email, password);
        assertEquals(personName, actual.name());
        assertEquals(email, actual.email());
        assertEquals(password, actual.password());
    }
}

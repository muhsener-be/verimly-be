package app.verimly.user.data;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.vo.PersonName;
import com.github.javafaker.Faker;

public class UserTestData {

    private final static UserTestData INSTANCE = new UserTestData();

    public static UserTestData getInstance() {
        return INSTANCE;
    }


    private static final Faker FAKER = new Faker();
    private static final String EMAIL_VALUE = "tester@email.com";


    public User user() {
        return User.create(
                personName(),
                email(),
                password().encrypted()
        );
    }

    public PasswordTestData password() {
        return PasswordTestData.getInstance();
    }

    public Email email() {
        return Email.of(EMAIL_VALUE);
    }

    public PersonName personName() {
        String firstName = FAKER.name().firstName();
        String lastName = FAKER.name().lastName();
        return PersonName.of(firstName, lastName);
    }

    public UserId userId() {
        return UserId.random();
    }
}

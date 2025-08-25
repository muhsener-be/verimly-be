package app.verimly.user.adapter.persistence.entity;

import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.vo.PersonName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityTest {

    UserTestData DATA;

    UUID id;
    String firstName;
    String lastName;
    String email;
    String password;

    @BeforeEach
    public void setup() {
        DATA = UserTestData.getInstance();
        id = DATA.userId().getValue();
        PersonName name = DATA.personName();
        firstName = name.getFirstName();
        lastName = name.getLastName();
        email = DATA.email().getValue();
        password = DATA.password().encrypted().getEncrypted();

    }

    @Test
    public void build_happy_path() {
        UserEntity actual = UserEntity.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();

        assertEquals(id, actual.getId());
        assertEquals(firstName, actual.getFirstName());
        assertEquals(lastName, actual.getLastName());
        assertEquals(email, actual.getEmail());
        assertEquals(password, actual.getPassword());


    }
}

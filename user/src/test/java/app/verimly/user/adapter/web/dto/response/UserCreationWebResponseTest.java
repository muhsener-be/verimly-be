package app.verimly.user.adapter.web.dto.response;

import app.verimly.user.data.UserTestData;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCreationWebResponseTest {
    Faker FAKER = new Faker();
    UUID id = UUID.randomUUID();
    String firstName = FAKER.name().firstName();
    String lastName = FAKER.name().lastName();
    String email = UserTestData.EMAIL_VALUE;

    @Test
    void build_happy_path() {
        UserCreationWebResponse actual = UserCreationWebResponse.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();

        assertEquals(id, actual.getId());
        assertEquals(firstName, actual.getFirstName());
        assertEquals(lastName, actual.getLastName());
        assertEquals(email, actual.getEmail());


    }
}

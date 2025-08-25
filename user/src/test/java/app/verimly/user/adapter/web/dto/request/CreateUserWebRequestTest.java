package app.verimly.user.adapter.web.dto.request;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserWebRequestTest {

    Faker FAKER = new Faker();
    private String firstName = FAKER.name().firstName();
    private String lastName = FAKER.name().lastName();
    private String email = "email@email.com";
    private String password = "password";

    @Test
    void of_happy_path() {
        CreateUserWebRequest actual = CreateUserWebRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();

        assertEquals(firstName, actual.getFirstName());
        assertEquals(lastName, actual.getLastName());
        assertEquals(email, actual.getEmail());
        assertEquals(password, actual.getPassword());
    }
}

package app.verimly.user.data;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.adapter.web.dto.request.CreateUserWebRequest;
import app.verimly.user.adapter.web.dto.response.UserCreationWebResponse;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.vo.PersonName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;

public class UserTestData {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private final static UserTestData INSTANCE = new UserTestData();

    public static UserTestData getInstance() {
        return INSTANCE;
    }


    private static final Faker FAKER = new Faker();
    public static final String EMAIL_VALUE = "tester@email.com";


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


    public CreateUserCommand createUserCommand() {
        return CreateUserCommand.of(
                personName(),
                email(),
                password().raw()
        );
    }

    public CreateUserWebRequest createUserWebRequest() {
        return CreateUserWebRequest.builder()
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .email(EMAIL_VALUE)
                .password(password().raw().getRaw())
                .build();

    }

    public UserCreationResponse userCreationResponse() {
        return UserCreationResponse.of(userId(), personName(), email());
    }

    public UserCreationWebResponse userCreationWebResponse() {
        PersonName personName = personName();
        return UserCreationWebResponse.builder()
                .id(userId().getValue())
                .firstName(personName.getFirstName())
                .lastName(personName.getLastName())
                .email(EMAIL_VALUE)
                .build();
    }


    public String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public UserEntity userEntity() {
        return UserEntity.builder()
                .id(userId().getValue())
                .password(password().encrypted().getEncrypted())
                .firstName(personName().getFirstName())
                .lastName(personName().getLastName())
                .email(email().getValue())
                .build();
    }
}

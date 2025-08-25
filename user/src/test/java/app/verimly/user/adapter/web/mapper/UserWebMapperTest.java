package app.verimly.user.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.user.adapter.web.dto.request.CreateUserWebRequest;
import app.verimly.user.adapter.web.dto.response.UserCreationWebResponse;
import app.verimly.user.application.mapper.UserAppMapperImpl;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.data.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {UserWebMapperImpl.class, CoreVoMapperImpl.class, UserAppMapperImpl.class})
public class UserWebMapperTest {

    UserTestData DATA = UserTestData.getInstance();

    CreateUserWebRequest createUserWebRequest = DATA.createUserWebRequest();
    UserCreationResponse userCreationResponse = DATA.userCreationResponse();

    @Autowired
    UserWebMapper userWebMapper;


    @Test
    void setup_is_ok() {
        assertNotNull(userWebMapper);
    }

    @Test
    void toUserCreationWebResponse_happy_path() {
        UserCreationWebResponse actual = userWebMapper.toUserCreationWebResponse(userCreationResponse);

        UserCreationResponse expected = userCreationResponse;
        assertEquals(expected.id().getValue(), actual.getId());
        assertEquals(expected.name().getFirstName(), actual.getFirstName());
        assertEquals(expected.name().getLastName(), actual.getLastName());
        assertEquals(expected.email().getValue(), actual.getEmail());
    }


    @Test
    void toCreateUserCommand_happy_path() {
        CreateUserCommand command = userWebMapper.toCreateUserCommand(createUserWebRequest);

        CreateUserWebRequest expected = createUserWebRequest;
        assertEquals(expected.getFirstName(), command.name().getFirstName());
        assertEquals(expected.getLastName(), command.name().getLastName());
        assertEquals(expected.getEmail(), command.email().getValue());
        assertEquals(expected.getPassword(), command.password().getRaw());


    }
}

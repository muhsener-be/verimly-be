package app.verimly.user.application.mapper;

import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserAppMapperTest {

    UserTestData DATA = UserTestData.getInstance();

    User user = DATA.user();

    UserAppMapper userAppMapper = Mappers.getMapper(UserAppMapper.class);

    @Test
    void toUserCreationResponse_happyPath() {
        UserCreationResponse response = userAppMapper.toUserCreationResponse(user);

        assertEquals(user.getId(), response.id());
        assertEquals(user.getName(), response.name());
        assertEquals(user.getEmail(), response.email());
    }
}

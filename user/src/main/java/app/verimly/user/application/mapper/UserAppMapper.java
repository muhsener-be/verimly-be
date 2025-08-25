package app.verimly.user.application.mapper;

import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAppMapper {


    UserCreationResponse toUserCreationResponse(User user);
}

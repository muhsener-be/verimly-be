package app.verimly.user.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.core.domain.mapper.ZonedTimeMapper;
import app.verimly.commons.core.security.SecurityUser;
import app.verimly.user.adapter.web.dto.request.CreateUserWebRequest;
import app.verimly.user.adapter.web.dto.response.UserCreationWebResponse;
import app.verimly.user.adapter.web.dto.response.UserDetailsWebResponse;
import app.verimly.user.application.dto.UserDetailsData;
import app.verimly.user.application.mapper.UserAppMapper;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class, UserAppMapper.class, ZonedTimeMapper.class})
public interface UserWebMapper {

    @Mapping(target = "firstName", source = "source.name.firstName")
    @Mapping(target = "lastName", source = "source.name.lastName")
    UserCreationWebResponse toUserCreationWebResponse(UserCreationResponse source);


    @Mapping(target = "name", expression = "java(userAppMapper.toPersonName(source.getFirstName(),source.getLastName()))")
    CreateUserCommand toCreateUserCommand(CreateUserWebRequest source);


    UserDetailsWebResponse toUserDetailsWebResponse(SecurityUser user);


    UserDetailsWebResponse toUserDetailsWebResponse(UserDetailsData user);

}

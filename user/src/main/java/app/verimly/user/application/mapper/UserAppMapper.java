package app.verimly.user.application.mapper;

import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.vo.Password;
import app.verimly.user.domain.vo.PersonName;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAppMapper {


    UserCreationResponse toUserCreationResponse(User user);

    default Password toPassword(String raw) {
        return Password.withRaw(raw);
    }

    default PersonName toPersonName(String firstName, String lastName){
        return PersonName.of(firstName,lastName);
    }
}

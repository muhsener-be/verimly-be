package app.verimly.user.adapter.messaging.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.commons.event.UserCreatedMessage;
import app.verimly.user.application.event.UserCreatedApplicationEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CoreVoMapperImpl.class})
public interface UserMessagingMapper {


    @Mapping(target = "name", source = "user.name.fullName")
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    UserCreatedMessage toUserCreatedMessage(UserCreatedApplicationEvent event);
}

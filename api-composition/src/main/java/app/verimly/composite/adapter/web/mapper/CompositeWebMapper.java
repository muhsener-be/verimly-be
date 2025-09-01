package app.verimly.composite.adapter.web.mapper;

import app.verimly.composite.adapter.web.dto.response.UserWithSessionsWebResponse;
import app.verimly.composite.application.usecase.fetch_user_profile_and_active_session.UserWithSessionsResponse;
import app.verimly.task.adapter.web.mapper.SessionWebMapper;
import app.verimly.user.adapter.web.mapper.UserWebMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserWebMapper.class, SessionWebMapper.class})
public interface CompositeWebMapper {


    UserWithSessionsWebResponse toUserWithSessionsWebResponse(UserWithSessionsResponse source);
}

package app.verimly.task.adapter.messaging.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.event.UserCreatedMessage;
import app.verimly.task.application.mapper.TaskVoMapper;
import app.verimly.task.application.ports.in.messaging.CreatedUserDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class, TaskVoMapper.class})
public interface TaskMessagingMapper {


    CreatedUserDetails toCreatedUserDetails(UserCreatedMessage message);
}

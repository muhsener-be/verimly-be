package app.verimly.task.application.mapper;


import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.input.TaskCreationDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskAppMapper {

    TaskCreationDetails toTaskCreationDetails(CreateTaskCommand command, UserId ownerId);

    TaskCreationResponse toTaskCreationResponse(Task source);
}

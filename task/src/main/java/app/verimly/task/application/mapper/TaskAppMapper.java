package app.verimly.task.application.mapper;


import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.ports.out.persistence.TaskSummaryProjection;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.input.TaskCreationDetails;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskAppMapper {

    TaskCreationDetails toTaskCreationDetails(CreateTaskCommand command, UserId ownerId);

    TaskCreationResponse toTaskCreationResponse(Task source);

    TaskSummaryData toTaskDetailsData(TaskSummaryProjection projection);

    List<TaskSummaryData> toTaskDetailsData(List<TaskSummaryProjection> projections);

}

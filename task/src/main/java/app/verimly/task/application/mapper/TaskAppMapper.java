package app.verimly.task.application.mapper;


import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.ports.out.persistence.TaskSummaryProjection;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.application.usecase.command.task.replace.ReplaceTaskCommand;
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


    default void mergeTaskFrom(ReplaceTaskCommand from, Task to) {
        Assert.notNull(from, "command cannot be null to merge task.");
        Assert.notNull(to, "task cannot be null to be merged.");


        to.rename(from.name());
        to.changeStatus(from.status());
        to.changeDueDate(from.dueDate());
        to.changePriority(from.priority());
        to.changeDescription(from.description());
    }

}

package app.verimly.task.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.task.adapter.web.dto.request.CreateTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.TaskCreationWebResponse;
import app.verimly.task.application.mapper.TaskVoMapper;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Mapper(componentModel = "spring", uses = {TaskVoMapper.class, CoreVoMapperImpl.class})
public interface TaskWebMapper {

    CreateTaskCommand toCreateTaskCommand(CreateTaskWebRequest request);

    @Mapping(target = "dueDate", ignore = true)
    TaskCreationWebResponse toTaskCreationWebResponseIgnoringDueDate(TaskCreationResponse response);

    default TaskCreationWebResponse toTaskCreationWebResponse(TaskCreationResponse response) {
        if (response == null)
            return null;

        TaskCreationWebResponse webResponse = toTaskCreationWebResponseIgnoringDueDate(response);
        if (response.dueDate() == null)
            return webResponse;


        Instant instant = response.dueDate().getValue();
        TimeZone userTimeZone = LocaleContextHolder.getTimeZone();

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, userTimeZone.toZoneId());
        webResponse.setDueDate(zonedDateTime);

        return webResponse;
    }
}

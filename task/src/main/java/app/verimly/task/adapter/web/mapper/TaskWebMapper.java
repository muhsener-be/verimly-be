package app.verimly.task.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.task.adapter.web.dto.request.CreateTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.TaskCreationWebResponse;
import app.verimly.task.adapter.web.dto.response.TaskSummaryWebResponse;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.mapper.TaskVoMapper;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import org.mapstruct.Mapper;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Mapper(componentModel = "spring", uses = {TaskVoMapper.class, CoreVoMapperImpl.class})
public interface TaskWebMapper {

    CreateTaskCommand toCreateTaskCommand(CreateTaskWebRequest request);


    TaskCreationWebResponse toTaskCreationWebResponse(TaskCreationResponse source);

    default ZonedDateTime toZonedDateTimeTimeZoneAware(Instant instant) {
        if (instant == null)
            return null;
        TimeZone userTimeZone = LocaleContextHolder.getTimeZone();
        return ZonedDateTime.ofInstant(instant, userTimeZone.toZoneId());

    }


    TaskSummaryWebResponse toTaskSummaryWebResponse(TaskSummaryData source);


    default List<TaskSummaryWebResponse> toTaskSummaryWebResponse(List<TaskSummaryData> source) {
        if (source == null)
            return null;

        return source.stream()
                .filter(Objects::nonNull)
                .map(this::toTaskSummaryWebResponse)
                .toList();

    }

}


package app.verimly.task.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.task.adapter.web.dto.request.StartSessionForTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.SessionStartWebResponse;
import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.mapper.TaskVoMapper;
import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class, TaskVoMapper.class, ZonedTimeMapper.class})
public interface SessionWebMapper {


    StartSessionForTaskCommand toStartSessionForTaskCommand(StartSessionForTaskWebRequest source);


    SessionStartWebResponse toWebResponse(SessionStartResponse response);

    SessionSummaryWebResponse toWebResponse(SessionSummaryData data);

}

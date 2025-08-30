package app.verimly.task.application.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.input.SessionCreationDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class, TaskVoMapper.class})
public interface SessionAppMapper {


    SessionCreationDetails toSessionCreationDetails(UserId ownerId, StartSessionForTaskCommand command);

    SessionStartResponse toSessionStartResponse(TimeSession session);

    SessionSummaryData toSessionSummaryData(TimeSession timeSession);
}

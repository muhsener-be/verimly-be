package app.verimly.task.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.core.domain.mapper.ZonedTimeMapper;
import app.verimly.task.adapter.web.SessionComparator;
import app.verimly.task.adapter.web.dto.aggregate.SessionWebResponseWithTotalTime;
import app.verimly.task.adapter.web.dto.request.StartSessionForTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.SessionStartWebResponse;
import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.mapper.TaskVoMapper;
import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class, TaskVoMapper.class, ZonedTimeMapper.class})
public interface SessionWebMapper {


    StartSessionForTaskCommand toStartSessionForTaskCommand(StartSessionForTaskWebRequest source);


    SessionStartWebResponse toWebResponse(SessionStartResponse response);

    @Mapping(target = "totalTime", source = ".", qualifiedByName = "calculateTotalTime")
    SessionSummaryWebResponse toWebResponse(SessionSummaryData data);

    @Named("calculateTotalTime")
    default Duration calculateTotalTime(SessionSummaryData session) {


        Instant start = session.getStartedAt();
        Instant end = null;

        if (session.isRunning())
            end = Instant.now();
        else if (session.isFinished())
            end = session.getFinishedAt();
        else
            end = session.getPausedAt();


        Duration totalTime = Duration.between(start, end);
        return totalTime.minus(session.getTotalPause());
    }

    default SessionWebResponseWithTotalTime toSessionWebResponseWithTotalTime(List<SessionSummaryData> datas) {
        final Duration[] totalTime = {Duration.ZERO};
        SessionComparator comparator = new SessionComparator();
        List<SessionSummaryWebResponse> list = datas.stream().sorted(comparator)
                .map(this::toWebResponse)
                .peek(webResponse -> totalTime[0] = totalTime[0].plus(webResponse.getTotalTime()))
                .toList();

        return new SessionWebResponseWithTotalTime(totalTime[0], list);

    }

}

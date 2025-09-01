package app.verimly.task.application.ports.in;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;

import java.util.List;

public interface SessionApplicationService {


    SessionStartResponse startSession(StartSessionForTaskCommand command);

    SessionSummaryData pauseSession(SessionId sessionId);

    SessionSummaryData resumeSession(SessionId sessionId);

    SessionSummaryData finishSession(SessionId sessionId);



    List<SessionSummaryData> fetchActiveSessions();
}

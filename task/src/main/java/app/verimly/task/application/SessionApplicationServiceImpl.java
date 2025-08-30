package app.verimly.task.application;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.ports.in.SessionApplicationService;
import app.verimly.task.application.usecase.command.session.change_status.FinishSessionCommandHandler;
import app.verimly.task.application.usecase.command.session.change_status.PauseSessionCommandHandler;
import app.verimly.task.application.usecase.command.session.change_status.ResumeSessionCommandHandler;
import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionApplicationServiceImpl implements SessionApplicationService {

    private final StartSessionForTaskCommandHandler startSessionForTaskCommandHandler;
    private final PauseSessionCommandHandler pauseSessionCommandHandler;
    private final ResumeSessionCommandHandler resumeSessionCommandHandler;
    private final FinishSessionCommandHandler finishSessionCommandHandler;


    @Override
    public SessionStartResponse startSession(StartSessionForTaskCommand command) {
        return startSessionForTaskCommandHandler.handle(command);
    }

    @Override
    public SessionSummaryData pauseSession(SessionId sessionId) {
        return pauseSessionCommandHandler.handle(sessionId);
    }

    @Override
    public SessionSummaryData resumeSession(SessionId sessionId) {
        return resumeSessionCommandHandler.handle(sessionId);
    }

    @Override
    public SessionSummaryData finishSession(SessionId sessionId) {
        return finishSessionCommandHandler.handle(sessionId);
    }
}

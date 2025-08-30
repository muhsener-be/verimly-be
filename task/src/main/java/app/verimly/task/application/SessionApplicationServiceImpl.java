package app.verimly.task.application;

import app.verimly.task.application.ports.in.SessionApplicationService;
import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionApplicationServiceImpl implements SessionApplicationService {

    private final StartSessionForTaskCommandHandler startSessionForTaskCommandHandler;


    @Override
    public SessionStartResponse startSession(StartSessionForTaskCommand command) {
        return startSessionForTaskCommandHandler.handle(command);
    }
}

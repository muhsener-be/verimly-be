package app.verimly.task.application.ports.in;

import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;

public interface SessionApplicationService {


    SessionStartResponse startSession(StartSessionForTaskCommand command);
}

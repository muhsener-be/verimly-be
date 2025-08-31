package app.verimly.task.application.exception;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.task.application.dto.SessionSummaryData;
import lombok.Getter;

@Getter
public class ActiveSessionExistsException extends SessionBusinessException {

    public static final String MESSAGE_TEMPLATE = "User has already running session. UserId: %s, TaskId: %s, SessionId: %s";
    private final SessionSummaryData session;
    private static final ErrorMessage ERROR_MESSAGE = ErrorMessage.of("session.running-exists", "A running session already exists.");


    public ActiveSessionExistsException(SessionSummaryData session) {
        super(ERROR_MESSAGE, MESSAGE_TEMPLATE.formatted(session.getOwnerId(), session.getTaskId(), session.getId()));
        this.session = session;
    }

}

package app.verimly.task.application.exception;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.NotFoundException;
import app.verimly.commons.core.domain.vo.SessionId;

public class SessionNotFoundException extends NotFoundException {

    public static ErrorMessage ERROR_MESSAGE = ErrorMessage.of("session.not-found", "Session not found.");

    public SessionNotFoundException(SessionId sessionId) {
        super(ERROR_MESSAGE.withDefaultMessage("Session not found with provided ID: %s".formatted(sessionId)));
    }
}

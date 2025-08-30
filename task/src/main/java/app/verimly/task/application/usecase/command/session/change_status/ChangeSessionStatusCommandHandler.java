package app.verimly.task.application.usecase.command.session.change_status;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.exception.SessionNotFoundException;
import app.verimly.task.application.mapper.SessionAppMapper;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.ChangeSessionStatusContext;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.repository.TimeSessionWriteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ChangeSessionStatusCommandHandler {

    protected final AuthenticationService authN;
    protected final TaskAuthorizationService authZ;
    protected final TimeSessionWriteRepository sessionRepository;
    protected final SessionAppMapper mapper;

    public abstract SessionSummaryData handle(SessionId sessionId);

    protected Principal getCurrentPrincipal() {
        return authN.getCurrentPrincipal();
    }


    protected void authorizeRequest(Principal principal, SessionId sessionId) throws SecurityException {
        ChangeSessionStatusContext context = ChangeSessionStatusContext.createWithSessionId(sessionId);
        authZ.authorizeChangeSessionStatus(principal, context);
    }


    protected TimeSession fetchSessionAndCheckExistence(SessionId sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() -> new SessionNotFoundException(sessionId));
    }

    protected TimeSession persistChanges(TimeSession session) {
        return sessionRepository.update(session);
    }

    protected SessionSummaryData mapToSessionSummaryData(TimeSession session) {
        return mapper.toSessionSummaryData(session);
    }
}

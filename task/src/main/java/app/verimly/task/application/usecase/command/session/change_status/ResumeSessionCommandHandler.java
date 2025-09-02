package app.verimly.task.application.usecase.command.session.change_status;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.exception.ActiveSessionExistsException;
import app.verimly.task.application.mapper.SessionAppMapper;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.repository.TimeSessionWriteRepository;
import app.verimly.task.domain.vo.session.SessionStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ResumeSessionCommandHandler extends ChangeSessionStatusCommandHandler {

    public ResumeSessionCommandHandler(AuthenticationService authN, TaskAuthorizationService authZ,
                                       TimeSessionWriteRepository sessionRepository, SessionAppMapper mapper) {
        super(authN, authZ, sessionRepository, mapper);
    }

    @Override
    @Transactional
    public SessionSummaryData handle(SessionId sessionId) {
        Principal principal = getCurrentPrincipal();
        authorizeRequest(principal, sessionId);

        ensureNoActiveSessionExist(principal.getId());


        TimeSession session = fetchSessionAndCheckExistence(sessionId);
        session.resume();
        TimeSession persisted = persistChanges(session);

        return mapToSessionSummaryData(persisted);

    }

    private void ensureNoActiveSessionExist(UserId principalId) {
        List<TimeSession> runningSessions = sessionRepository.findByOwnerIdAndStatus(principalId, SessionStatus.RUNNING);
        if (!runningSessions.isEmpty()) {
            SessionSummaryData sessionData = mapper.toSessionSummaryData(runningSessions.getFirst());
            throw new ActiveSessionExistsException(sessionData);
        }
    }
}

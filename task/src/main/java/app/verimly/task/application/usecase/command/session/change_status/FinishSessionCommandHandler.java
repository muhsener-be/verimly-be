package app.verimly.task.application.usecase.command.session.change_status;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.mapper.SessionAppMapper;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.repository.TimeSessionWriteRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FinishSessionCommandHandler extends ChangeSessionStatusCommandHandler {

    public FinishSessionCommandHandler(AuthenticationService authN, TaskAuthorizationService authZ, TimeSessionWriteRepository sessionRepository, SessionAppMapper mapper) {
        super(authN, authZ, sessionRepository, mapper);
    }

    @Override
    @Transactional
    public SessionSummaryData handle(SessionId sessionId) {
        Principal principal = super.getCurrentPrincipal();
        super.authorizeRequest(principal, sessionId);

        TimeSession session = fetchSessionAndCheckExistence(sessionId);

        session.finish();

        TimeSession persisted = persistChanges(session);
        return mapToSessionSummaryData(persisted);
    }
}

package app.verimly.task.application.usecase.query.session.fetch_active;

import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.adapter.web.SessionComparator;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.ports.out.persistence.SessionReadRepository;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.ViewSessionContext;
import app.verimly.task.domain.vo.session.SessionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FetchActiveSessionQueryHandler {
    private final AuthenticationService authN;
    private final SessionReadRepository sessionRepository;
    private final TaskAuthorizationService taskAuthorizationService;

    @Transactional
    public List<SessionSummaryData> handle() {
        Principal principal = authN.getCurrentPrincipal();

        authorizeRequest(principal);

        return fetchSessionsAndSort(principal);

    }

    private @NotNull List<SessionSummaryData> fetchSessionsAndSort(Principal principal) {
        List<SessionSummaryData> activeSessions = sessionRepository.findSessionsByStatusForUser(principal.getId(), SessionStatus.RUNNING, SessionStatus.PAUSED);
        activeSessions.sort(new SessionComparator());
        return activeSessions;
    }

    private void authorizeRequest(Principal principal) {
        ViewSessionContext viewSessionContext = new ViewSessionContext();
        taskAuthorizationService.authorizeViewSession(principal, viewSessionContext);
    }
}

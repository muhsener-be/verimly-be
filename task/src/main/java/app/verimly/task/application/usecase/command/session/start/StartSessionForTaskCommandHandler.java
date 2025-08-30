package app.verimly.task.application.usecase.command.session.start;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.exception.ActiveSessionExistsException;
import app.verimly.task.application.exception.TaskNotFoundException;
import app.verimly.task.application.mapper.SessionAppMapper;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.StartSessionContext;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.input.SessionCreationDetails;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.repository.TimeSessionWriteRepository;
import app.verimly.task.domain.service.TimeSessionDomainService;
import app.verimly.task.domain.vo.session.SessionStatus;
import app.verimly.task.domain.vo.task.TaskId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StartSessionForTaskCommandHandler {

    private final AuthenticationService authN;
    private final TaskAuthorizationService authZ;
    private final TaskWriteRepository taskRepository;
    private final TimeSessionWriteRepository sessionRepository;
    private final SessionAppMapper mapper;
    private final TimeSessionDomainService domainService;

    @Transactional
    public SessionStartResponse handle(StartSessionForTaskCommand command) {
        Assert.notNull(command, "command cannot be null to start session for task.");


        Principal principal = authN.getCurrentPrincipal();
        authorizeRequest(principal, command);

        Task task = fetchTaskAndCheckExistence(command.taskId());
        ensureUserHasNoRunningSessions(principal.getId());

        TimeSession sessionStarted = startSession(command, principal.getId(), task);

        TimeSession persistedSession = sessionRepository.save(sessionStarted);

        return prepareResponse(persistedSession);


    }

    private void ensureUserHasNoRunningSessions(UserId id) {
        List<TimeSession> activeSessions = sessionRepository.findByOwnerIdAndStatus(id, SessionStatus.RUNNING);
        if (activeSessions.isEmpty())
            return;

        TimeSession activeSession = activeSessions.getFirst();
        SessionSummaryData activeSessionData = mapper.toSessionSummaryData(activeSession);
        throw new ActiveSessionExistsException(activeSessionData);

    }

    private SessionStartResponse prepareResponse(TimeSession session) {
        return mapper.toSessionStartResponse(session);
    }

    private TimeSession startSession(StartSessionForTaskCommand command, UserId principalId, Task task) {
        SessionCreationDetails details = mapper.toSessionCreationDetails(principalId, command);
        return domainService.startSessionForTask(task, details);
    }

    private void authorizeRequest(Principal principal, StartSessionForTaskCommand command) throws SecurityException {
        StartSessionContext context = StartSessionContext.createWithTaskId(command.taskId());
        authZ.authorizeStartSession(principal, context);
    }

    private Task fetchTaskAndCheckExistence(TaskId taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    }
}

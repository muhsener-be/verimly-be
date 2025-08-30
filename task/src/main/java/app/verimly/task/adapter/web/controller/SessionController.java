package app.verimly.task.adapter.web.controller;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.task.adapter.web.dto.common.SessionStatusAction;
import app.verimly.task.adapter.web.dto.request.ChangeSessionStatusWebRequest;
import app.verimly.task.adapter.web.dto.request.StartSessionForTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.SessionStartWebResponse;
import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import app.verimly.task.adapter.web.mapper.SessionWebMapper;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.ports.in.SessionApplicationService;
import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionWebMapper mapper;
    private final SessionApplicationService applicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionStartWebResponse startSessionForTask(@Valid @RequestBody StartSessionForTaskWebRequest request) {
        StartSessionForTaskCommand command = mapper.toStartSessionForTaskCommand(request);
        SessionStartResponse response = applicationService.startSession(command);

        return mapper.toWebResponse(response);
    }


    @PutMapping("/{sessionId}/status")
    public SessionSummaryWebResponse actOnSessionStatus(@Valid @RequestBody ChangeSessionStatusWebRequest request, @PathVariable("sessionId") UUID sessionUUID) {
        String actionString = request.getAction();
        SessionStatusAction action = SessionStatusAction.of(actionString);
        SessionId sessionId = SessionId.of(sessionUUID);

        SessionSummaryData sessionSummaryData = null;
        switch (action) {
            case PAUSE -> sessionSummaryData = applicationService.pauseSession(sessionId);
            case RESUME -> sessionSummaryData = applicationService.resumeSession(sessionId);
            case FINISH -> sessionSummaryData = applicationService.finishSession(sessionId);
            default -> throw new IllegalStateException("Unknown action type: " + action);
        }
        ;

        return mapper.toWebResponse(sessionSummaryData);

    }
}

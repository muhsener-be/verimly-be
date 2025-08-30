package app.verimly.task.adapter.web.controller;

import app.verimly.task.adapter.web.dto.request.StartSessionForTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.SessionStartWebResponse;
import app.verimly.task.adapter.web.mapper.SessionWebMapper;
import app.verimly.task.application.ports.in.SessionApplicationService;
import app.verimly.task.application.usecase.command.session.start.SessionStartResponse;
import app.verimly.task.application.usecase.command.session.start.StartSessionForTaskCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}

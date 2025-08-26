package app.verimly.user.adapter.web.controller;

import app.verimly.user.adapter.web.doc.CreateUserSpringDoc;
import app.verimly.user.adapter.web.dto.request.CreateUserWebRequest;
import app.verimly.user.adapter.web.dto.response.UserCreationWebResponse;
import app.verimly.user.adapter.web.mapper.UserWebMapper;
import app.verimly.user.application.ports.in.UserApplicationService;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserWebMapper webMapper;
    private final UserApplicationService applicationService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @CreateUserSpringDoc
    public UserCreationWebResponse create(@RequestBody @Valid CreateUserWebRequest request) {
        CreateUserCommand command = webMapper.toCreateUserCommand(request);
        UserCreationResponse response = applicationService.create(command);

        return webMapper.toUserCreationWebResponse(response);
    }

}

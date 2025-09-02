package app.verimly.composite.adapter.web;

import app.verimly.composite.adapter.web.dto.response.UserWithSessionsWebResponse;
import app.verimly.composite.adapter.web.mapper.CompositeWebMapper;
import app.verimly.composite.application.ports.in.CompositeApiApplicationService;
import app.verimly.composite.application.usecase.fetch_user_profile_and_active_session.UserWithSessionsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
@Tag(name = "User" , description = "APIs for manage user.")
public class ProfileController {

    private final CompositeApiApplicationService applicationService;
    private final CompositeWebMapper mapper;


    @GetMapping("/profile")
    @UserWithSessionSpringDoc
    public UserWithSessionsWebResponse fetchUserDetailsAndActiveSession() {
        UserWithSessionsResponse response = applicationService.fetchUserAndActiveSessions();

        return mapper.toUserWithSessionsWebResponse(response);
    }
}

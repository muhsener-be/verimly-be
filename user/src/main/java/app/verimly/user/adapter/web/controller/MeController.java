package app.verimly.user.adapter.web.controller;

import app.verimly.user.adapter.web.doc.GetUserDetailsSpringDoc;
import app.verimly.user.adapter.web.dto.response.UserDetailsWebResponse;
import app.verimly.user.adapter.web.mapper.UserWebMapper;
import app.verimly.user.application.dto.UserDetailsData;
import app.verimly.user.application.ports.in.UserApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
@Tag(name = "User" , description = "APIs for manage user.")
public class MeController {

    private final UserApplicationService userApplicationService;
    private final UserWebMapper userWebMapper;

    @GetMapping()
    @GetUserDetailsSpringDoc
    public ResponseEntity<UserDetailsWebResponse> get() {
        UserDetailsData data = userApplicationService.fetchUserDetails();

        UserDetailsWebResponse webResponse = userWebMapper.toUserDetailsWebResponse(data);
        return ResponseEntity.ok(webResponse);


    }
}

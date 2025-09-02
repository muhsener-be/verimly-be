package app.verimly.user.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "APIs for authentication.")
public class AuthController {


    @Operation(
            summary = "Log out the current user",
            description = "Logs out the currently authenticated user by invalidating the access_token cookie."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Logout successful. The 'access_token' cookie is cleared.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized If user request contains access_token cookie but it is valid or verified..",
                    ref = "#/components/responses/UnauthenticatedResponse"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void logout() {


        throw new IllegalStateException("This endpoint should not be called. Logout logic implemented by Spring's security filter chain.");
    }



    @Operation(
            summary = "Login user",
            description = "Authenticates a user with email and password (form data)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized if credentials are invalid.",
                    ref = "#/components/responses/UnauthenticatedResponse"
            )
    })
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        throw new IllegalStateException("This endpoint should not be called. Login logic is handled by Spring Security.");
    }



}

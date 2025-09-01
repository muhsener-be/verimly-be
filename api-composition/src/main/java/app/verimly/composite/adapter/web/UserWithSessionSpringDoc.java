package app.verimly.composite.adapter.web;

import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.composite.adapter.web.dto.response.UserWithSessionsWebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OpenAPI documentation for getting authenticated user's profile with active sessions.
 * <p>
 * Use this annotation on the fetchUserDetailsAndActiveSession() method of ProfileController
 * to document the API in the Swagger UI.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get user profile with active sessions",
        description = "Retrieves the profile details along with active sessions for the currently authenticated user.\n\n" +
                "**Active Sessions:** Includes both RUNNING and PAUSED sessions. The first session in the list will be the RUNNING one if it exists. " +
                "The remaining sessions are sorted by their start time in descending order (newest first).\n\n" +
                "**Authentication:** Requires a valid session token in the 'access_token' HTTP-only cookie.\n" +
                "**Required permissions:** User must be authenticated.",
        security = {}
        // Note: Security is handled via HTTP-only cookie named 'access_token'
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "User profile and active sessions retrieved successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserWithSessionsWebResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized - Authentication required",
                ref = "#/components/responses/UnauthenticatedResponse"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found - If User not found.",
                ref = "#/components/responses/NotFoundResponse"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        )
})
public @interface UserWithSessionSpringDoc {
}

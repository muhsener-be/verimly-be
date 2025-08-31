package app.verimly.user.adapter.web.doc;

import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.user.adapter.web.dto.response.UserDetailsWebResponse;
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
 * OpenAPI documentation for getting authenticated user's details endpoint.
 * <p>
 * Use this annotation on the get() method of MeController to document the API in Swagger UI.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get authenticated user's details",
        description = "Retrieves the details of the currently authenticated user.\n\n" +
                    "**Authentication:** Requires a valid session token in the 'access_token' HTTP-only cookie.\n" +
                    "**Required permissions:** User must be authenticated.",
        security = {}
        // Note: Security is handled via HTTP-only cookie named 'access_token'
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "User details retrieved successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserDetailsWebResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized - Authentication required",
                ref = "#/components/responses/UnauthenticatedResponse"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Forbidden - Insufficient permissions",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found",
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
public @interface GetUserDetailsSpringDoc {
}

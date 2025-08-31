package app.verimly.task.adapter.web.docs;

import app.verimly.task.adapter.web.dto.request.StartSessionForTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.SessionStartWebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OpenAPI documentation for starting a new session for a task.
 * <p>
 * Use this annotation on the startSessionForTask method in SessionController
 * to document the API in Swagger UI.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Start a new session for a task",
        description = "Initiates a new work session for the specified task.\n\n" +
                "**Authentication:** Requires a valid session token in the 'access_token' HTTP-only cookie.\n" +
                "**Required permissions:** User must have permission to work on the specified task.",
        requestBody = @RequestBody(
                description = "Session details",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = StartSessionForTaskWebRequest.class)
                )
        ),
        security = {}
        // Note: Security is handled via HTTP-only cookie named 'access_token'
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "Session started successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = SessionStartWebResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request data",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = app.verimly.commons.core.web.response.ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Validation Error",
                                summary = "When request validation fails",
                                value = """
                                        {
                                            "timestamp": "2025-08-31T12:00:00+03:00",
                                            "status": 400,
                                            "error": "Bad Request",
                                            "message": "Validation failed",
                                            "path": "/api/v1/sessions"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized - Authentication required",
                ref = "#/components/responses/UnauthenticatedResponse"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Forbidden - User doesn't have permission to access the task",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = app.verimly.commons.core.web.response.ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Task not found",
                ref = "#/components/responses/NotFoundResponse"
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Active session already exists",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = app.verimly.commons.core.web.response.ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = app.verimly.commons.core.web.response.ErrorResponse.class)
                )
        )
})
public @interface StartSessionSpringDoc {
}

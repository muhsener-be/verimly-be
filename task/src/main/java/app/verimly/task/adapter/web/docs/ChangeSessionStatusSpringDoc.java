package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.BadRequestErrorResponse;
import app.verimly.commons.core.web.response.NoPermissionErrorResponse;
import app.verimly.commons.core.web.response.NotFoundErrorResponse;
import app.verimly.commons.docs.ApiExamples;
import app.verimly.task.adapter.web.dto.request.ChangeSessionStatusWebRequest;
import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
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
 * OpenAPI documentation for changing session status endpoint.
 * <p>
 * This endpoint allows pausing, resuming, or finishing a work session.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Change session status",
        description = "Allows changing the status of a work session.\n\n" +
                "**Authentication:** Requires a valid session token in the 'access_token' HTTP-only cookie.\n" +
                "**Required permissions:** User must own the session.",
        requestBody = @RequestBody(
                description = "Session status change request",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ChangeSessionStatusWebRequest.class),
                        examples = @ExampleObject(
                                name = "Pause Session Example",
                                summary = "Example request to pause a session",
                                value = """
                                        {
                                            "action": "PAUSE"
                                        }
                                        """
                        )
                )
        )
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Session status changed successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = SessionSummaryWebResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request data or invalid transition. For example, finished session cannot be paused.",
                content = @Content(
                        schema = @Schema(implementation = BadRequestErrorResponse.class),
                        examples = @ExampleObject(value = ApiExamples.BAD_REQUEST)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized - Authentication required",
                ref = "#/components/responses/UnauthenticatedResponse"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Forbidden - User doesn't have permission to modify this session",
                content = @Content(
                        schema = @Schema(implementation = NoPermissionErrorResponse.class),
                        examples = @ExampleObject(ApiExamples.FORBIDDEN)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Session not found",
                content = @Content(
                        schema = @Schema(implementation = NotFoundErrorResponse.class),
                        examples = @ExampleObject(TaskApiExamples.SESSION_NOT_FOUND)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                ref = "#/components/responses/InternalErrorResponse"
        )
})
public @interface ChangeSessionStatusSpringDoc {
}

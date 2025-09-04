package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.BadRequestErrorResponse;
import app.verimly.commons.core.web.response.ConflictErrorResponse;
import app.verimly.commons.core.web.response.NoPermissionErrorResponse;
import app.verimly.commons.core.web.response.NotFoundErrorResponse;
import app.verimly.commons.docs.ApiExamples;
import app.verimly.task.adapter.web.dto.request.StartSessionForTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.SessionStartWebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

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
                description = "Forbidden - User doesn't have permission to access the task",
                content = @Content(
                        schema = @Schema(implementation = NoPermissionErrorResponse.class),
                        examples = @ExampleObject(ApiExamples.FORBIDDEN)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Task not found",
                content = @Content(
                        schema = @Schema(implementation = NotFoundErrorResponse.class),
                        examples = @ExampleObject(TaskApiExamples.TASK_NOT_FOUND)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Active session already exists",
                content = @Content(
                        schema = @Schema(implementation = ConflictErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Session conflict",
                                value = """
                                        {
                                             "timestamp": "2025-09-03T10:45:21.985991Z",
                                              "status":  409,
                                              "path" : "/api/v1/resource",
                                              "error_code": "active.session",
                                              "message": "Active session already exist.",
                                              "resourceType": "SESSION",
                                              "resourceId":  "d290f1ee-6c54-4b01-90e6-d701748f0851",
                                              "conflictResource": {
                                                       "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
                                                       "name": "Morning coding session",
                                                       "status": "RUNNING",
                                                       "owner_id": "550e8400-e29b-41d4-a716-446655440000",
                                                       "task_id": "550e8400-e29b-41d4-a716-446655440001",
                                                       "started_at": "2025-01-01T09:00:00+03:00",
                                                       "paused_at": "2025-01-01T10:30:00+03:00",
                                                       "finished_at": "2025-01-01T11:30:00+03:00",
                                                       "total_pause": "PT30M",
                                                       "total_time": "PT2H"
                                             }
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                ref = "#/components/responses/InternalErrorResponse"
        )
})
public @interface StartSessionSpringDoc {
}

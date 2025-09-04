package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.BadRequestErrorResponse;
import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.commons.core.web.response.NoPermissionErrorResponse;
import app.verimly.commons.core.web.response.NotFoundErrorResponse;
import app.verimly.commons.docs.ApiExamples;
import app.verimly.task.adapter.web.dto.aggregate.TaskWithSessionsWebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get task with sessions",
        description = "Retrieves detailed information about a specific task including all its associated work sessions."
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Task and sessions retrieved successfully",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = TaskWithSessionsWebResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid task ID format",
                content = @Content(
                        schema = @Schema(implementation = BadRequestErrorResponse.class),
                        examples = @ExampleObject(value = ApiExamples.BAD_REQUEST)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthenticated",
                ref = "#/components/responses/UnauthenticatedResponse"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Forbidden - User doesn't have permission to view this task",
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
                responseCode = "500",
                description = "Internal Server Error",
                ref = "#/components/responses/InternalErrorResponse"
        )
})
public @interface FetchTaskWithSessionSpringDoc {
}

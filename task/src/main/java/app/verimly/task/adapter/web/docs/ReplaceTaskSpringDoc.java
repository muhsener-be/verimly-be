package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.BadRequestErrorResponse;
import app.verimly.commons.core.web.response.NoPermissionErrorResponse;
import app.verimly.commons.core.web.response.NotFoundErrorResponse;
import app.verimly.commons.docs.ApiExamples;
import app.verimly.task.adapter.web.dto.response.TaskSummaryWebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SpringDoc annotation for the Replace Task API endpoint.
 * <p>
 * This annotation documents the API for replacing an existing task with new details.
 * It describes possible responses, including success, validation errors, authentication, authorization, and not found errors.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Replace a task",
        description = "Replaces an existing task with the provided details and returns the updated task information."
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Task replaced successfully",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = TaskSummaryWebResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request data",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = BadRequestErrorResponse.class),
                        examples = @ExampleObject(value = ApiExamples.BAD_REQUEST)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthenticated - Authentication required to access this endpoint.",
                ref = "#/components/responses/UnauthenticatedResponse"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Forbidden - User doesn't have permission to update this task.",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = NoPermissionErrorResponse.class),
                        examples = @ExampleObject(value = ApiExamples.FORBIDDEN)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Task not found",
                content = @Content(
                        schema = @Schema(implementation = NotFoundErrorResponse.class),
                        examples = @ExampleObject(value = TaskApiExamples.TASK_NOT_FOUND)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                ref = "#/components/responses/InternalErrorResponse"
        )
})
public @interface ReplaceTaskSpringDoc {
}

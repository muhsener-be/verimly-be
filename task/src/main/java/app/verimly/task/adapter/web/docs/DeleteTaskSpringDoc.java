package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.BadRequestErrorResponse;
import app.verimly.commons.core.web.response.NoPermissionErrorResponse;
import app.verimly.commons.core.web.response.NotFoundErrorResponse;
import app.verimly.commons.docs.ApiExamples;
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

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Delete a task",
        description = "Deletes an existing task by its ID. (Soft Delete)"
)
@ApiResponses({
        @ApiResponse(
                responseCode = "204",
                description = "Task deleted successfully"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid task ID format",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
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
                description = "Forbidden - User doesn't have permission to delete this task",
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

public @interface DeleteTaskSpringDoc {
}

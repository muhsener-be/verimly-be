package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.docs.ApiExamples;
import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.task.adapter.web.dto.response.TaskCreationWebResponse;
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
        summary = "Create a new task",
        description = "Creates a new task with the provided details and returns the created task information."
)
@ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Task created successfully",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = TaskCreationWebResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request data",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        examples = @ExampleObject(ApiExamples.UNAUTHORIZED)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Folder not found",
                ref = "#/components/responses/NotFoundResponse"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        examples = @ExampleObject(value = ApiExamples.INTERNAL)
                )
        )
})

public @interface CreateTaskSpringDoc {
}

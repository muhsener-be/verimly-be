package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Move a task to another folder",
        description = "Moves the specified task to a different folder."
)
@Parameter(name = "taskId" , description = "ID of the task you want to move." , in = ParameterIn.PATH,example = "123e4567-e89b-12d3-a456-426614174000")
@ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task moved successfully"),
        @ApiResponse(
                responseCode = "404",
                description = "Task not found or Folder not found",
                ref = "#/components/responses/NotFoundResponse"
        ),
        @ApiResponse(responseCode = "401", description = "User is not authenticated", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "User has no permission", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),

})
public @interface MoveToFolderSpringDoc {
}

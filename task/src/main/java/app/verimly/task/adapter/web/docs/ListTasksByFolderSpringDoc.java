package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.task.adapter.web.dto.response.TaskSummaryWebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
        summary = "List tasks by folder",
        description = "Returns a list of tasks for the specified folder. ZonedDateTime fields in the response are adjusted according to the user's timezone, which is obtained from the 'X-TimeZone' request header."
)
@Parameters({
        @Parameter(name = "folderId", description = "UUID of the folder to list tasks from", required = true, example = "b456f1ee-6c54-4b01-90e6-d701748f0851"),
        @Parameter(name = "X-TimeZone", description = "User's timezone, used to adjust ZonedDateTime fields in the response",
                in = io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER, required = false, example = "Europe/Istanbul")
})
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of tasks returned successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskSummaryWebResponse.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid folderId supplied", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden to view folder", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
                responseCode = "404",
                description = "Folder not found",
                ref = "#/components/responses/NotFoundResponse"
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))

})
public @interface ListTasksByFolderSpringDoc {
}

package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.task.adapter.web.dto.response.FolderSummaryWebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Operation(
        summary = "List all folders",
        description = "Retrieves a list of summaries for all folders accessible by the user."
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Folders listed successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = FolderSummaryWebResponse.class)))
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Authentication required",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
})
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ListFoldersSpringDoc {
}

package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.BadRequestErrorResponse;
import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.task.adapter.web.dto.response.FolderCreationWebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Operation(
        summary = "Create a new folder",
        description = "Creates a new folder with the given details. Returns the created folder's information."
)
@ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Folder created successfully",
                content = @Content(
                        schema = @Schema(implementation = FolderCreationWebResponse.class))
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid input data",
                content = @Content(
                        schema = @Schema(implementation = BadRequestErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Folder creation invalid input",
                                value = """
                                        {
                                             "timestamp": "2025-09-03T10:45:21.985991Z",
                                              "status": 400,
                                              "path": "/api/v1/folders",
                                              "message": "invalid input",
                                              "error_code": "bad-request",
                                              "error_codes":{
                                                 "folder.name-length": "Folder name can be at most x characters.",
                                                 "folder.description-length": "Folder description can be at most x characters."
                                                 "color.hex-format": "Label color must be in hex format."
                                              }
                                         }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Authentication required",
                ref = "#/components/responses/UnauthenticatedResponse"
        ),
        @ApiResponse(
                responseCode = "500",
                ref = "#/components/responses/InternalErrorResponse"

        )
})
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CreateFolderSpringDocs {
}

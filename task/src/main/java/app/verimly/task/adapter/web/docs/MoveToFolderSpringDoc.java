package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.commons.core.web.response.NotFoundErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
@Parameter(name = "taskId", description = "ID of the task you want to move.", in = ParameterIn.PATH, example = "123e4567-e89b-12d3-a456-426614174000")
@ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task moved successfully"),
        @ApiResponse(
                responseCode = "404",
                description = "Task not found or Folder not found",
                content = @Content(
                        schema = @Schema(implementation = NotFoundErrorResponse.class),
                        examples = {
                                @ExampleObject(
                                        name = "Task not found",
                                        value = TaskApiExamples.TASK_NOT_FOUND
                                ),
                                @ExampleObject(
                                        name = "Folder not found",
                                        value = TaskApiExamples.FOLDER_NOT_FOUND
                                )
                        }

                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "User is not authenticated",
                ref = "#/components/responses/UnauthenticatedResponse"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "User has no permission",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = {
                                @ExampleObject(
                                        name = "Not folder owner",
                                        value = """
                                                {
                                                    "timestamp": "2025-09-04T13:58:08.475372Z",
                                                    "status": 403,
                                                    "path": "uri=/api/v1/tasks/a21b9134-4a46-489a-aee1-9ded67e3f688/folder",
                                                    "message": "Principal 50ac87d9-dab0-4c65-bd14-41a91de036df has no permission to perform ADD_TASK on folder:f17b9134-4a46-489a-aee1-9ded67e3f688 (required: OWNERSHIP)",
                                                    "principal": "50ac87d9-dab0-4c65-bd14-41a91de036df",
                                                    "action": "ADD_TASK",
                                                    "resource": "folder:f17b9134-4a46-489a-aee1-9ded67e3f688",
                                                    "requirement": "OWNERSHIP",
                                                    "error_code": "forbidden"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Not task owner",
                                        value = """
                                                {
                                                    "timestamp": "2025-09-04T13:58:08.475372Z",
                                                    "status": 403,
                                                    "path": "uri=/api/v1/tasks/a21b9134-4a46-489a-aee1-9ded67e3f688/folder",
                                                    "message": "Principal 50ac87d9-dab0-4c65-bd14-41a91de036df has no permission to perform MOVE_TASK on task:f17b9134-4a46-489a-aee1-9ded67e3f688 (required: OWNERSHIP)",
                                                    "principal": "50ac87d9-dab0-4c65-bd14-41a91de036df",
                                                    "action": "MOVE_TASK",
                                                    "resource": "task:f17b9134-4a46-489a-aee1-9ded67e3f688",
                                                    "requirement": "OWNERSHIP",
                                                    "error_code": "forbidden"
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error.",
                ref = "#/components/responses/InternalErrorResponse"
        ),

})

public @interface MoveToFolderSpringDoc {
}

package app.verimly.task.adapter.web.docs;

import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.commons.core.web.response.NoPermissionErrorResponse;
import app.verimly.commons.core.web.response.NotFoundErrorResponse;
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
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(value =
                                """
                                        {
                                            "timestamp": "2025-09-03T10:45:21.985991Z",
                                             "status": 400,
                                             "path": "/api/v1/tasks",
                                             "message": "invalid input",
                                             "error_code": "bad-request",
                                             "error_codes":{
                                                "task.name-not-exist": "Task name is required.",
                                                 "due-date.past": "Due date cannot be in the past."
                                             }
                                        }
                                        """)
                )
        ),
        @ApiResponse(
                responseCode = "403",
                description = "User has no permission to create a task in folder.",
                content = @Content(
                        schema = @Schema(implementation = NoPermissionErrorResponse.class),
                        examples = @ExampleObject(value =
                                """
                                        {
                                         "timestamp", "2025-09-03T10:45:21.985991Z",
                                         "status", 403,
                                         "path", "/api/v1/tasks",
                                         "message", "Principal 'd290f1ee-6c54-4b01-90e6-d701748f0851' has no permission to perform ADD_TASK on FOLDER (required: OWNERSHIP)",
                                         "error_code", "resource-not-found",
                                         "resourceType", "FOLDER",
                                         "resourceId", "d290f1ee-6c54-4b01-90e6-d701748f0851"
                                        }
                                        """)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                ref = "#/components/responses/UnauthenticatedResponse"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Folder not found to assign task.",
                content = @Content(
                        schema = @Schema(implementation = NotFoundErrorResponse.class),
                        examples = @ExampleObject(value =
                                """
                                        {
                                        "timestamp", "2025-09-03T10:45:21.985991Z",
                                        "status", 404,
                                        "path", "/api/v1/tasks",
                                        "message", "No such Folder found with provided ID: 'd290f1ee-6c54-4b01-90e6-d701748f0851'",
                                        "error_code", "resource-not-found",
                                        "resourceType", "FOLDER",
                                        "resourceId", "d290f1ee-6c54-4b01-90e6-d701748f0851"
                                        }
                                        """)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                ref = "#/components/responses/InternalErrorResponse"
        )
})

public @interface CreateTaskSpringDoc {
}

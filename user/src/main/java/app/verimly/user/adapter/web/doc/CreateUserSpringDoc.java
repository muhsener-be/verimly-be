package app.verimly.user.adapter.web.doc;

import app.verimly.commons.core.web.response.BadRequestErrorResponse;
import app.verimly.commons.core.web.response.ConflictErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

/**
 * OpenAPI documentation for  create user endpoint.
 * <p>
 * Use this annotation on the create() method of UserController to document the API in Swagger UI.
 */
@Operation(
        summary = "Create a new user",
        description = "Creates a new user with the provided information. Returns the created user's details."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = app.verimly.user.adapter.web.dto.response.UserCreationWebResponse.class))),
        @ApiResponse(responseCode = "400",
                description = "Invalid input data",
                content = @Content(
                        schema = @Schema(implementation = BadRequestErrorResponse.class),
                        examples = @ExampleObject(
                                name = "User creation invalid input",
                                value = """
                                        {
                                            "timestamp": "2025-09-03T10:45:21.985991Z",
                                             "status": 400,
                                             "path": "/api/v1/users",
                                             "message": "invalid input",
                                             "error_code": "bad-request",
                                             "error_codes":{
                                                "password.white-space": "Password cannot contain any white-space.",
                                                 "first-name.required": "First name is required."
                                             }
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Email address already exists",
                content = @Content(
                        schema = @Schema(implementation = ConflictErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Email conflict",
                                description = "When email already exists",
                                value = """
                                        {
                                             "timestamp": "2025-09-03T10:45:21.985991Z",
                                              "status":  409,
                                              "path" : "/api/v1/users",
                                              "error_code": "email.duplicated",
                                              "message": "invalid input",
                                              "resourceType": "USER",
                                              "resourceId":  "null",
                                              "conflictResource": {
                                                    "email" : "conflicting@email.com"
                                              }
                                        }
                                        """
                        )
                )
        ),

        @ApiResponse(
                responseCode = "500",
                ref = "#/components/responses/InternalErrorResponse"
        )

})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CreateUserSpringDoc {
}

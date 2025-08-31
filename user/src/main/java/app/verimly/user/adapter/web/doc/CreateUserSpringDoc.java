package app.verimly.user.adapter.web.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
                ref = "#/components/responses/BadRequestResponse"
        ),
        @ApiResponse(responseCode = "409", description = "Email address already exists",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = app.verimly.commons.core.web.response.ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = app.verimly.commons.core.web.response.ErrorResponse.class)))
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CreateUserSpringDoc {
}

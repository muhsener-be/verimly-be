package app.verimly.config;

import app.verimly.commons.core.web.response.*;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        //noinspection rawtypes
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("AbstractErrorResponse", ModelConverters.getInstance().read(AbstractErrorResponse.class).get("AbstractErrorResponse"))
                        .addSchemas("NotFoundErrorResponse", ModelConverters.getInstance().read(NotFoundErrorResponse.class).get("NotFoundErrorResponse")
                                .example(Map.of(
                                                "timestamp", "2025-09-03T10:45:21.985991Z",
                                                "status", 404,
                                                "path", "/api/v1/resource",
                                                "message", "No such Task found with provided ID: 'd290f1ee-6c54-4b01-90e6-d701748f0851'",
                                                "error_code", "resource-not-found",
                                                "resourceType", "TASK",
                                                "resourceId", "d290f1ee-6c54-4b01-90e6-d701748f0851"
                                        )
                                )
                        )
                        .addSchemas("UnauthenticatedErrorResponse", ModelConverters.getInstance().read(UnauthenticatedErrorResponse.class).get("UnauthenticatedErrorResponse")
                                .example(
                                        Map.of(
                                                "timestamp", "2025-09-03T10:45:21.985991Z",
                                                "status", 401,
                                                "path", "/api/protected/resource",
                                                "message", "Full authorization required to access this resource.",
                                                "error_code", "unauthorized"
                                        )
                                )
                        )
                        .addSchemas("BadRequestErrorResponse", ModelConverters.getInstance().read(BadRequestErrorResponse.class).get("BadRequestErrorResponse")
                                .example(Map.of(
                                                "timestamp", "2025-09-03T10:45:21.985991Z",
                                                "status", 400,
                                                "path", "/api/v1/resource",
                                                "message", "invalid input",
                                                "error_code", "bad-request",
                                                "error_codes", Map.of(
                                                        "password.white-space", "Password cannot contain any white-space.",
                                                        "first-name.required", "First name is required."
                                                )
                                        )
                                )
                        )
                        .addSchemas("NoPermissionErrorResponse", ModelConverters.getInstance().read(NoPermissionErrorResponse.class).get("NoPermissionErrorResponse"))
                        .addSchemas("ConflictErrorResponse", ModelConverters.getInstance().read(ConflictErrorResponse.class).get("ConflictErrorResponse")
                                .example(Map.of(
                                                "timestamp", "2025-09-03T10:45:21.985991Z",
                                                "status", 400,
                                                "path", "/api/v1/resource",
                                                "error_code", "email.duplicated",
                                                "message", "invalid input",
                                                "resourceType", "USER",
                                                "resourceId", "null",
                                                "conflictResource", Map.of(
                                                        "email", "conflicting@email.com"
                                                )
                                        )
                                )
                        )
                        .addSchemas("InternalErrorResponse", ModelConverters.getInstance().read(InternalErrorResponse.class).get("InternalErrorResponse")
                                .example(Map.of(
                                                "timestamp", "2025-09-03T10:45:21.985991Z",
                                                "status", 500,
                                                "path", "/api/v1/resource",
                                                "error_code", "internal-server-error",
                                                "message", "Unexpected error happened."
                                        )
                                )
                        )


                        .addResponses("NotFoundResponse", new ApiResponse()
                                .description("Resource not found.")
                                .content(new Content().addMediaType("application/json", new MediaType()
                                                .schema(new Schema().$ref("#/components/schemas/NotFoundErrorResponse"))
                                        )
                                )
                        )
                        .addResponses("InternalErrorResponse", new ApiResponse()
                                .description("Internal Server Error")
                                .content(new Content().addMediaType("application/json", new MediaType()
                                                .schema(new Schema().$ref("#/components/schemas/InternalErrorResponse"))
                                        )
                                )
                        )
                        .addResponses("UnauthenticatedResponse", new ApiResponse()
                                .description("Unauthorized - Login is required")
                                .content(new Content().addMediaType("application/json", new MediaType()
                                                .schema(new Schema().$ref("#/components/schemas/UnauthenticatedErrorResponse"))
                                        )
                                )
                        )
                        .addResponses("BadRequestResponse", new ApiResponse()
                                .description("BadRequest")
                                .content(new Content().addMediaType("application/json", new MediaType()
                                                .schema(new Schema().$ref("#/components/schemas/BadRequestErrorResponse"))
                                        )
                                )
                        )
                        .addResponses("NoPermissionResponse", new ApiResponse()
                                .description("Forbidden")
                                .content(new Content().addMediaType("application/json", new MediaType()
                                                .schema(new Schema().$ref("#/components/schemas/NoPermissionErrorResponse"))
                                        )
                                )
                        )
                );
    }


}

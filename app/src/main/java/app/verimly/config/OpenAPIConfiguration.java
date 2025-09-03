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

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        //noinspection rawtypes
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("AbstractErrorResponse", ModelConverters.getInstance().read(AbstractErrorResponse.class).get("AbstractErrorResponse"))
                        .addSchemas("NotFoundErrorResponse", ModelConverters.getInstance().read(NotFoundErrorResponse.class).get("NotFoundErrorResponse"))
                        .addSchemas("UnauthenticatedErrorResponse", ModelConverters.getInstance().read(UnauthenticatedErrorResponse.class).get("UnauthenticatedErrorResponse"))
                        .addSchemas("BadRequestErrorResponse", ModelConverters.getInstance().read(BadRequestErrorResponse.class).get("BadRequestErrorResponse"))
                        .addSchemas("NoPermissionErrorResponse", ModelConverters.getInstance().read(NoPermissionErrorResponse.class).get("NoPermissionErrorResponse"))


                        .addResponses("NotFoundResponse", new ApiResponse()
                                .description("Resource not found.")
                                .content(new Content().addMediaType("application/json", new MediaType()
                                                .schema(new Schema().$ref("#/components/schemas/NotFoundErrorResponse"))
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
                                .description("NoPermission")
                                .content(new Content().addMediaType("application/json", new MediaType()
                                                .schema(new Schema().$ref("#/components/schemas/NoPermissionErrorResponse"))
                                        )
                                )
                        )
                );
    }


}

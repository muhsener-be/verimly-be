package app.verimly.commons.core.web.response;

import org.springframework.stereotype.Component;

@Component
public class ErrorResponseFactory {

    public NotFoundErrorResponse.NotFoundErrorResponseBuilder notFound() {
        return new NotFoundErrorResponse.NotFoundErrorResponseBuilder();
    }


    public UnauthenticatedErrorResponse.UnauthenticatedErrorResponseBuilder unauthenticated() {
        return new UnauthenticatedErrorResponse.UnauthenticatedErrorResponseBuilder();
    }

    public BadRequestErrorResponse.BadRequestErrorResponseBuilder badRequest() {
        return new BadRequestErrorResponse.BadRequestErrorResponseBuilder();
    }

    public ConflictErrorResponse.ConflictErrorResponseBuilder conflict(String path, String resourceType) {
        return new ConflictErrorResponse.ConflictErrorResponseBuilder().path(path).resourceType(resourceType);
    }
}

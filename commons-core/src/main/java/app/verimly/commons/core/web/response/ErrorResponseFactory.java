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
}

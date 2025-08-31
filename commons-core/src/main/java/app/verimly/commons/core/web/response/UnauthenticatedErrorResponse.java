package app.verimly.commons.core.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Schema(name = "UnauthenticatedErrorResponse")
public class UnauthenticatedErrorResponse extends AbstractErrorResponse {


    @Builder
    public UnauthenticatedErrorResponse(String path, String message) {
        super(Instant.now(), 401, path, message);
    }
}

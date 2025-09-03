package app.verimly.commons.core.web.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Getter
public class InternalErrorResponse extends AbstractErrorResponse {


    @Builder
    public InternalErrorResponse(String path, String message) {
        super(Instant.now(), 500, path, message, "internal-server-error");
    }
}

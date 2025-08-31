package app.verimly.commons.core.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class BadRequestErrorResponse extends AbstractErrorResponse {
    @JsonProperty("error_codes")
    @Schema(description = "A map of machine-readable error code to i18n message for it.",
            nullable = true,
            example = """
                    {
                        "password.white-space" : "Password cannot contain any white-spaces.",
                        "first-name.required" : "First name is required"
                    }
                    """
    )
    private Map<String, String> errors = new HashMap<>();


    @Builder
    public BadRequestErrorResponse(String path, String message, Map<String, String> errors) {
        super(Instant.now(), 400, path, message, "bad-request");
        this.errors = errors;
    }
}

package app.verimly.commons.core.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Schema(name = "NoPermissionErrorResponse")
public class NoPermissionErrorResponse extends AbstractErrorResponse {


    @Schema(description = "The principal (e.g., user) identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String principal;

    @Schema(description = "The action attempted by the principal", example = "VIEW_TASK")
    private String action;

    @Schema(description = "The resource on which action performed")
    private String resource;

    @Schema(description = "The required permission or role", example = "(OWNERSHIP or ROLE_ADMIN)")
    private String requirement;


    @Builder
    public NoPermissionErrorResponse(String path, String principal, String action, String resource, String requirement, String message) {
        super(Instant.now(), HttpStatus.FORBIDDEN.value(), path, message, "forbidden");
        this.principal = principal;
        this.action = action;
        this.requirement = requirement;
        this.resource = resource;
    }
}

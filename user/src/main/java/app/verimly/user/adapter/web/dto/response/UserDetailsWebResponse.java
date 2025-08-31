package app.verimly.user.adapter.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Detailed information about a user, including personal and system metadata")
public class UserDetailsWebResponse {

    @Schema(description = "Unique identifier of the user", 
            example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Full name of the user", 
            example = "John Doe")
    private String name;

    @Schema(description = "Email address of the user", 
            example = "john.doe@example.com",
            format = "email")
    private String email;

    @JsonProperty("created_at")
    @Schema(description = "Timestamp when the user account was created in ISO-8601 format", 
            example = "2025-01-01T12:00:00+03:00")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    @Schema(description = "Timestamp when the user account was last updated in ISO-8601 format", 
            example = "2025-01-15T15:30:00+03:00")
    private ZonedDateTime updatedAt;
}

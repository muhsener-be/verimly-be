package app.verimly.user.adapter.web.dto.request;

import app.verimly.user.adapter.web.validation.EmailFormat;
import app.verimly.user.domain.vo.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Request object for creating a new user")
public class CreateUserWebRequest {

    @Schema(description = "User's first name", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "first-name.required")
    @Length(min = 1, max = 50, message = "first-name.length")
    @JsonProperty("first_name")
    private String firstName;

    @Schema(description = "User's last name", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "last-name.required")
    @Length(min = 1, max = 50, message = "last-name.length")
    @JsonProperty("last_name")
    private String lastName;

    @Schema(description = "User's email address", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @EmailFormat(message = "email.format")
    @NotNull(message = "email.required")
    private String email;

    @Schema(description = "User's password. Must be 8-30 characters, no white spaces.", example = "MyS3cretPwd", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = Password.MIN_LENGTH, max = Password.MAX_LENGTH, message = "password.length")
    @Pattern(regexp = "^\\S*$", message = "password.white-space")
    @NotNull(message = "password.required")
    private String password;


}

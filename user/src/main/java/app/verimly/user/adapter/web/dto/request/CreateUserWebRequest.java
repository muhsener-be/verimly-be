package app.verimly.user.adapter.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateUserWebRequest {


    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")

    private String lastName;
    private String email;
    private String password;


}

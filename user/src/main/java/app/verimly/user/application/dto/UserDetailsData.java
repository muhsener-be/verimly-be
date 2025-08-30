package app.verimly.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;


@AllArgsConstructor
@Builder
@Data
public class UserDetailsData {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private Instant createdAt;

    private Instant updatedAt;

    public String getName(){
        return firstName + " " + lastName;
    }
}

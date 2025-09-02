package app.verimly.commons.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserCreatedMessage {

    private Instant occurredAt;
    private UUID id;
    private String name;
    private String email;


}

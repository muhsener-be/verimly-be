package app.verimly.user.adapter.persistence.entity;

import app.verimly.commons.core.adapter.persistence.BaseJpaEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserEntity extends BaseJpaEntity<UUID> {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Builder
    public UserEntity(UUID id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}

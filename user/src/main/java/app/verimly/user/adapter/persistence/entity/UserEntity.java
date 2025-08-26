package app.verimly.user.adapter.persistence.entity;

import app.verimly.commons.core.adapter.persistence.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "u_users_email", columnNames = "email"))
@Entity
@Setter
public class UserEntity extends BaseJpaEntity<UUID> {
    public static final String EMAIL_UNIQUE_CONSTRAINT_NAME = "u_users_email";

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
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

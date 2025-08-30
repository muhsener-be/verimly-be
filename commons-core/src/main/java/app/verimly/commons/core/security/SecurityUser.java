package app.verimly.commons.core.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SecurityUser implements UserDetails {
    @Getter
    private final UUID id;
    private final String password;
    private final String email;
    @Getter
    private final String name;

    public SecurityUser(UUID id, String email, String password) {
        this(id, email, password, null);
    }

    public SecurityUser(UUID id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail(){
        return this.getUsername();
    }

}

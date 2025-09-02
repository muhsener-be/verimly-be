package app.verimly.bootstrap;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("bootstrap.user")
@Component
@Data
public class BootstrapUserProps {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

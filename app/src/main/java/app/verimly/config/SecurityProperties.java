package app.verimly.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("security")
@Data
public class SecurityProperties {
    private String signUpPath;
    private String frontEndOrigin;
    private CorsProperties cors;

}

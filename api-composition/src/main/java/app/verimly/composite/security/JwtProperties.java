package app.verimly.composite.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("security.jwt")
@Component
public class JwtProperties {
    private String secretKey;
    private String emailClaim;
    private long expiresIn;
    private String issuer;
}

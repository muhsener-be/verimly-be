package app.verimly.composite.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("security.access-token-cookie")
@Data
public class AccessTokenCookieProperties {
    private String name;
    private boolean secure;
    private boolean httpOnly;
    private String sameSite;
    private int maxAgeSeconds;
    private String path;
    private String domain;

}

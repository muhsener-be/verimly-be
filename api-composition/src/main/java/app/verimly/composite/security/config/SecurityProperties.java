package app.verimly.composite.security.config;

import app.verimly.composite.security.CorsProperties;
import app.verimly.composite.security.JwtProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("security")
@Data
public class SecurityProperties {
    private String signUpPath;
    private String frontEndOrigin;
    private String loginPath;
    private String usernameParameter;
    private String passwordParameter;
    private CorsProperties cors;
    private JwtProperties jwt;
    private AccessTokenCookieProperties accessTokenCookie;



}

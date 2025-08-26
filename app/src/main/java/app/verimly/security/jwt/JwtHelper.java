package app.verimly.security.jwt;

import app.verimly.config.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtHelper {

    private final JwtProperties properties;

    public String generateJwtToken(String subject, String email) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(properties.getExpiresIn());
        return JWT.create()
                .withSubject(subject)
                .withClaim(properties.getEmailClaim(), email)
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withIssuer(properties.getIssuer())
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
}

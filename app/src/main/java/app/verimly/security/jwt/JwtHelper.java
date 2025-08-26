package app.verimly.security.jwt;

import app.verimly.config.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class JwtHelper {

    private final JwtProperties properties;
    private final Algorithm algorithm;

    public JwtHelper(JwtProperties properties) {
        this.properties = properties;
        algorithm = Algorithm.HMAC256(properties.getSecretKey());
    }

    public String generateJwtToken(String subject, String email) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(properties.getExpiresIn());
        return JWT.create()
                .withSubject(subject)
                .withClaim(properties.getEmailClaim(), email)
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withIssuer(properties.getIssuer())
                .sign(algorithm);
    }

    public VerifiedToken verifyToken(String token) {
        try {
            DecodedJWT verify = JWT.require(algorithm).build().verify(token);
            String subject = verify.getSubject();
            String emailClaim = verify.getClaim(properties.getEmailClaim()).asString();
            return new VerifiedToken(UUID.fromString(subject), emailClaim);
        } catch (JWTVerificationException e) {
            throw new JwtException(e.getMessage(), e);
        }

    }
}

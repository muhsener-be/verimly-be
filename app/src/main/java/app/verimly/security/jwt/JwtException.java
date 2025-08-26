package app.verimly.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtException extends AuthenticationException {
    public JwtException(String message, Throwable e) {
        super(message, e);
    }
}

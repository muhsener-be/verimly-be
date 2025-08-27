package app.verimly.security.cookie;

import app.verimly.config.AccessTokenCookieProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieHelper {

    private final AccessTokenCookieProperties props;

    public ResponseCookie createAccessTokenCookie(String token) {

        return ResponseCookie.from(props.getName())
                .value(token)
                .path(props.getPath())
                .maxAge(props.getMaxAgeSeconds())
                .httpOnly(props.isHttpOnly())
                .sameSite(props.getSameSite())
                .domain(props.getDomain())
                .secure(props.isSecure())
                .build();


    }
}

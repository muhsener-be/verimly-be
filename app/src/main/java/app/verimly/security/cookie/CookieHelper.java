package app.verimly.security.cookie;

import app.verimly.config.AccessTokenCookieProperties;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieHelper {

    private final AccessTokenCookieProperties props;

    public Cookie createAccessTokenCookie(String token) {
        Cookie cookie = new Cookie(props.getName(), token);
        cookie.setDomain(props.getDomain());
        cookie.setHttpOnly(props.isHttpOnly());
        cookie.setSecure(props.isSecure());
        cookie.setMaxAge(props.getMaxAgeSeconds());
        cookie.setPath(props.getPath());

        return cookie;
    }
}

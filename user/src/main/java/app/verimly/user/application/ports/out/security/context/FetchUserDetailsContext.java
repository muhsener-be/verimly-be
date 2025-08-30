package app.verimly.user.application.ports.out.security.context;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthorizationContext;
import lombok.Getter;

@Getter
public class FetchUserDetailsContext implements AuthorizationContext {

    private final UserId userId;

    private FetchUserDetailsContext(UserId userId) {
        this.userId = userId;
    }

    public static FetchUserDetailsContext createWithUserId(UserId userId) {
        return new FetchUserDetailsContext(userId);
    }
}

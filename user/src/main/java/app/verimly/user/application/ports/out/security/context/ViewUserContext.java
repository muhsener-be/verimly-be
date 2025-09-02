package app.verimly.user.application.ports.out.security.context;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthorizationContext;
import lombok.Getter;

@Getter
public class ViewUserContext implements AuthorizationContext {

    private final UserId userId;

    private ViewUserContext(UserId userId) {
        this.userId = userId;
    }

    public static ViewUserContext createWithUserId(UserId userId) {
        return new ViewUserContext(userId);
    }
}

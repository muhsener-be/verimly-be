package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.security.AuthorizationContext;
import lombok.Getter;

@Getter
public class SessionAuthorizationContext implements AuthorizationContext {
    private SessionId sessionId;

    public SessionAuthorizationContext() {
    }

    public SessionAuthorizationContext(SessionId sessionId) {
        this.sessionId = sessionId;
    }
}

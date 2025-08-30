package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.SessionId;

public class ChangeSessionStatusContext extends SessionAuthorizationContext {

    private ChangeSessionStatusContext(SessionId sessionId) {
        super(sessionId);
    }

    public static ChangeSessionStatusContext createWithSessionId(SessionId sessionId) {
        Assert.notNull(sessionId, "Session Id cannot be null to construct ChangeSessionStatusContext");
        return new ChangeSessionStatusContext(sessionId);

    }
}

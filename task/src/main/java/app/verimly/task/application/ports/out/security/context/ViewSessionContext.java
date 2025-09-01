package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.vo.SessionId;

public class ViewSessionContext extends SessionAuthorizationContext {

    public ViewSessionContext() {
    }

    public ViewSessionContext(SessionId sessionId) {
        super(sessionId);
    }
}

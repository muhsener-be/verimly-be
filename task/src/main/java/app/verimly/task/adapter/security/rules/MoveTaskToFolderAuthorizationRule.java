package app.verimly.task.adapter.security.rules;

import app.verimly.commons.core.security.Action;
import app.verimly.commons.core.security.AuthResource;
import app.verimly.commons.core.security.AuthorizationRule;
import app.verimly.commons.core.security.Principal;

public class MoveTaskToFolderAuthorizationRule implements AuthorizationRule {
    @Override
    public void apply(Principal principal, AuthResource resource) {

    }

    @Override
    public Action getSupportedAction() {
        return null;
    }
}

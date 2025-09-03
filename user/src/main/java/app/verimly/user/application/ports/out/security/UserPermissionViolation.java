package app.verimly.user.application.ports.out.security;

import app.verimly.commons.core.security.PermissionRequirement;
import app.verimly.commons.core.security.PermissionViolation;

import java.util.List;
import java.util.UUID;

public class UserPermissionViolation extends PermissionViolation {

    private UserPermissionViolation(UUID principal, String action, String resource, List<PermissionRequirement> requirement) {
        super(principal, action, resource, requirement);
    }

    public static UserPermissionViolation viewUser(UUID principalId, UUID userId) {
        return new UserPermissionViolation(principalId, "VIEW_USER", "user:" + userId.toString(), List.of(PermissionRequirement.OWNERSHIP));
    }
}

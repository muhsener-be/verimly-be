package app.verimly.commons.core.security;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public abstract class PermissionViolation {

    UUID principal;
    String action;
    String resource;
    List<PermissionRequirement> requirement;

    protected PermissionViolation(UUID principal, String action, String resource, List<PermissionRequirement> requirement) {
        this.principal = principal;
        this.action = action;
        this.resource = resource;
        this.requirement = requirement;
    }
}

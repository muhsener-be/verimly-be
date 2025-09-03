package app.verimly.commons.core.security;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class PermissionViolation {

    private UUID principal;
    private String action;
    private String resource;
    private PermissionRequirement requirement;

    protected PermissionViolation(UUID principal, String action, String resource, PermissionRequirement requirements) {
        this.principal = principal;
        this.action = action;
        this.resource = resource;
        this.requirement = requirements;
    }
}

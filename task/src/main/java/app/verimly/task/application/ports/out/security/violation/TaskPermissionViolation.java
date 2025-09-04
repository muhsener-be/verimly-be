package app.verimly.task.application.ports.out.security.violation;

import app.verimly.commons.core.security.PermissionRequirement;
import app.verimly.commons.core.security.PermissionViolation;

import java.util.UUID;

public class TaskPermissionViolation extends PermissionViolation {



    public TaskPermissionViolation(UUID principal, String action, String resource, PermissionRequirement requirements) {
        super(principal, action, resource, requirements);
    }
}

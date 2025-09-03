package app.verimly.task.application.ports.out.security.violation;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.PermissionRequirement;
import app.verimly.commons.core.security.PermissionViolation;
import app.verimly.task.domain.vo.folder.FolderId;

import java.util.UUID;

public class FolderPermissionViolation extends PermissionViolation {


    private FolderPermissionViolation(UUID principal, String action, String resource, PermissionRequirement requirements) {
        super(principal, action, resource, requirements);
    }


    public static FolderPermissionViolation addTask(UserId principalId, FolderId folderId) {
        return addTask(principalId, folderId, PermissionRequirement.of("OWNERSHIP"));
    }

    public static FolderPermissionViolation addTask(UserId principalId, FolderId folderId, PermissionRequirement requirement) {
        return new FolderPermissionViolation(principalId.getValue(), "ADD_TASK", "folder:" + folderId.toString(), requirement);
    }
}

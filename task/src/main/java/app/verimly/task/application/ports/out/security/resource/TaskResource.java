package app.verimly.task.application.ports.out.security.resource;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthResource;
import app.verimly.task.domain.vo.folder.FolderId;

public record TaskResource(UserId ownerId, FolderId folderId) implements AuthResource {
    public static TaskResource of(UserId ownerId, FolderId folderId) {
        return new TaskResource(ownerId, folderId);
    }

    public TaskResource withFolderId(FolderId folderId) {
        return new TaskResource(this.ownerId, folderId);
    }

    public AuthResource withOwnerId(UserId ownerId) {
        return new TaskResource(ownerId, folderId);
    }
}

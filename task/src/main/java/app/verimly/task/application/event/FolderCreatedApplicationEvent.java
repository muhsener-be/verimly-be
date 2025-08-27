package app.verimly.task.application.event;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.domain.entity.Folder;
import jakarta.validation.constraints.NotNull;

public record FolderCreatedApplicationEvent(@NotNull Principal actor, @NotNull Folder folder) {

    public FolderCreatedApplicationEvent {
        Assert.notNull(actor, "Actor cannot be null.");
        Assert.notNull(folder, "folder cannot be null.");
    }

}

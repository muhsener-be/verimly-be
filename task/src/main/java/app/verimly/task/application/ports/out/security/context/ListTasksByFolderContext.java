package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.task.domain.vo.folder.FolderId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ListTasksByFolderContext extends TaskAuthorizationContext {

    private final FolderId folderId;

    private ListTasksByFolderContext(FolderId folderId) {
        super();
        this.folderId = folderId;
    }

    public static ListTasksByFolderContext createWithFolderId(@NotNull FolderId folderId) {
        Assert.notNull(folderId, "folderId cannot be null");
        return new ListTasksByFolderContext(folderId);
    }
}

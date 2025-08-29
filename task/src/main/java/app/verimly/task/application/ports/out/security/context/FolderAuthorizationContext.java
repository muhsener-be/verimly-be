package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.security.AuthorizationContext;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.Getter;


@Getter
public abstract class FolderAuthorizationContext implements AuthorizationContext {
    private FolderId id;

    public FolderAuthorizationContext() {
    }

    public FolderAuthorizationContext(FolderId id) {
        this.id = id;
    }
}

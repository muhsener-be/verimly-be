package app.verimly.task.application.ports.out.security.resource;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthResource;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.Getter;


@Getter
public class FolderResource implements AuthResource {
    private final FolderId id;
    private final UserId ownerId;

    public FolderResource(FolderId id, UserId ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }


}

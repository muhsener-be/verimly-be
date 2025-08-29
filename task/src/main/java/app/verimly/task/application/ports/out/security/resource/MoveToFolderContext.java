package app.verimly.task.application.ports.out.security.resource;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.vo.folder.FolderId;

public class MoveToFolderContext extends FolderResource {


    public MoveToFolderContext(FolderId id, UserId ownerId) {
        super(id, ownerId);
    }
}

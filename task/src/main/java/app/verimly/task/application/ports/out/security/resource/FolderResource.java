package app.verimly.task.application.ports.out.security;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthResource;
import app.verimly.task.domain.vo.folder.FolderId;



public record FolderResource(FolderId id, UserId ownerId) implements AuthResource {

}

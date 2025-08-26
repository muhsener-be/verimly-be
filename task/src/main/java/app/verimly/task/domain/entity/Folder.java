package app.verimly.task.domain.entity;

import app.verimly.commons.core.domain.entity.BaseEntity;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.exception.FolderDomainException;
import app.verimly.task.domain.vo.FolderId;
import app.verimly.task.domain.vo.FolderName;
import lombok.Getter;

@Getter
public class Folder extends BaseEntity<FolderId> {

    private FolderName name;
    private UserId ownerId;

    protected Folder(FolderId id, UserId ownerId, FolderName name) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
    }

    public static Folder create(FolderName name, UserId ownerId) {
        Folder folder = new Folder(FolderId.random(), ownerId, name);
        folder.checkInvariants();
        return folder;
    }

    private void checkInvariants() {
        if (this.name == null)
            throw new FolderDomainException(Errors.NAME_NOT_EXIST);

        if (this.ownerId == null)
            throw new FolderDomainException(Errors.OWNER_NOT_EXIST);


    }

    public static final class Errors {
        public static final ErrorMessage NAME_NOT_EXIST = ErrorMessage.of("folder.name-not-exist", "Folder must have a name");
        public static final ErrorMessage OWNER_NOT_EXIST = ErrorMessage.of("folder.owner-not-exist", "Folder must have an owner.");
    }
}

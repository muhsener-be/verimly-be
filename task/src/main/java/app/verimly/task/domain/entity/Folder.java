package app.verimly.task.domain.entity;

import app.verimly.commons.core.domain.entity.BaseEntity;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.Color;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.exception.FolderDomainException;
import app.verimly.task.domain.vo.folder.FolderDescription;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.folder.FolderName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class Folder extends BaseEntity<FolderId> {

    private FolderName name;
    private UserId ownerId;
    private FolderDescription description;
    private Color labelColor;


    protected Folder(FolderId id, UserId ownerId, FolderName name, @Nullable FolderDescription description, @Nullable Color labelColor) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.description = description;
        this.labelColor = labelColor;
    }

    public static Folder create(@NotNull UserId ownerId, @NotNull FolderName name) {
        return createWithDescriptionAndLabelColor(ownerId, name, null, null);
    }

    public static Folder createWithDescription(@NotNull UserId ownerId, @NotNull FolderName name, @Nullable FolderDescription description) {
        return createWithDescriptionAndLabelColor(ownerId, name, description, null);
    }

    public static Folder createWithDescriptionAndLabelColor(@NotNull UserId ownerId,
                                                            @NotNull FolderName name,
                                                            @Nullable FolderDescription description,
                                                            @Nullable Color labelColor) {
        FolderId randomId = FolderId.random();
        Folder folder = new Folder(randomId, ownerId, name, description, labelColor);
        folder.checkInvariants();
        return folder;
    }

    public static Folder reconstruct(FolderId id,
                                     UserId ownerId,
                                     FolderName name,
                                     FolderDescription description,
                                     Color labelColor) {
        return new Folder(id, ownerId, name, description, labelColor);
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

package app.verimly.task.domain.vo;

import app.verimly.commons.core.domain.vo.BaseId;

import java.util.UUID;

public class FolderId extends BaseId<UUID> {

    protected FolderId(UUID value) {
        super(value);
    }

    public static FolderId of(UUID value) {
        return value == null ? null : new FolderId(value);
    }

    public static FolderId random() {
        return new FolderId(UUID.randomUUID());
    }


}

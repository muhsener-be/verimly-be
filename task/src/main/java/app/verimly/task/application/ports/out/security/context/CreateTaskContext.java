package app.verimly.task.application.ports.out.security.context;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.task.domain.vo.folder.FolderId;
import lombok.Getter;

@Getter
public class CreateTaskContext extends TaskAuthorizationContext {
    private final FolderId folderId;

    private CreateTaskContext(FolderId folderId) {
        this.folderId = folderId;
    }


    public static CreateTaskContext createWithFolderId(FolderId folderId) {
        Assert.notNull(folderId, "FolderId cannot be null to construct CreateTaskContext");
        return new CreateTaskContext(folderId);
    }
}

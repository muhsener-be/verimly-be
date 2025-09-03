package app.verimly.task.application.exception;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.NotFoundException;
import app.verimly.task.domain.vo.folder.FolderId;

public class FolderNotFoundException extends NotFoundException {

    public static ErrorMessage ERROR_MESSAGE = ErrorMessage.of("folder.not-found", "Folder not found.");

    public FolderNotFoundException(FolderId folderId) {
        super("FOLDER", folderId.toString());
    }

    public FolderNotFoundException(FolderId id, String message) {
        super("FOLDER", id.toString(), message);
    }


}

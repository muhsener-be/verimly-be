package app.verimly.task.application.usecase.command.create;


import app.verimly.commons.core.domain.vo.Color;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.vo.FolderDescription;
import app.verimly.task.domain.vo.FolderId;
import app.verimly.task.domain.vo.FolderName;

public record FolderCreationResponse(FolderId id, UserId ownerId, FolderName name, FolderDescription description, Color labelColor) {
}

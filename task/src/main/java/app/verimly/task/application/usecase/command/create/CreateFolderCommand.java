package app.verimly.task.application.usecase.command.create;

import app.verimly.commons.core.domain.vo.Color;
import app.verimly.task.domain.vo.FolderDescription;
import app.verimly.task.domain.vo.FolderName;


public record CreateFolderCommand(FolderName name, FolderDescription description, Color labelColor) {
}

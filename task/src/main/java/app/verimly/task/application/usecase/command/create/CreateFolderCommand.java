package app.verimly.task.application.usecase.command.create;

import app.verimly.task.domain.vo.FolderName;


public record CreateFolderCommand(FolderName name) {
}

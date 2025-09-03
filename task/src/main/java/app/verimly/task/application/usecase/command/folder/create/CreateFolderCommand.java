package app.verimly.task.application.usecase.command.folder.create;

import app.verimly.commons.core.domain.vo.Color;
import app.verimly.task.domain.vo.folder.FolderDescription;
import app.verimly.task.domain.vo.folder.FolderName;


public record CreateFolderCommand(FolderName name, FolderDescription description, Color labelColor) {
    public CreateFolderCommand withName(FolderName name) {
        return new CreateFolderCommand(name, description, labelColor);
    }
}

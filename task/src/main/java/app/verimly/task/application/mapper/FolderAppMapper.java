package app.verimly.task.application.mapper;

import app.verimly.task.application.usecase.command.create.FolderCreationResponse;
import app.verimly.task.domain.entity.Folder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FolderAppMapper {

    FolderCreationResponse toFolderCreationResponse(Folder folder);
}

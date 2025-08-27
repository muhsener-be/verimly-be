package app.verimly.task.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.task.adapter.web.dto.request.CreateFolderWebRequest;
import app.verimly.task.adapter.web.dto.response.FolderCreationWebResponse;
import app.verimly.task.application.mapper.TaskVoMapper;
import app.verimly.task.application.usecase.command.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.create.FolderCreationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TaskVoMapper.class, CoreVoMapper.class})
public interface FolderWebMapper {


    CreateFolderCommand toCreateFolderCommand(CreateFolderWebRequest source);

    FolderCreationWebResponse toFolderCreationWebResponse(FolderCreationResponse source);

}

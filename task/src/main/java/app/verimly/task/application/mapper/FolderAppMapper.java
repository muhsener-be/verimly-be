package app.verimly.task.application.mapper;

import app.verimly.task.application.dto.FolderSummaryData;
import app.verimly.task.application.ports.out.persistence.FolderSummaryProjection;
import app.verimly.task.application.usecase.command.create.FolderCreationResponse;
import app.verimly.task.domain.entity.Folder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FolderAppMapper {

    FolderCreationResponse toFolderCreationResponse(Folder folder);

    FolderSummaryData toFolderSummaryData(FolderSummaryProjection source);

    List<FolderSummaryData> toFolderSummaryData(List<FolderSummaryProjection> source);
}

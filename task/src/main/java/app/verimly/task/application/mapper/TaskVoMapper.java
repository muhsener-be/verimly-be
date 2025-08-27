package app.verimly.task.application.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.task.domain.vo.folder.FolderDescription;
import app.verimly.task.domain.vo.folder.FolderName;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = CoreVoMapper.class)
public interface TaskVoMapper {


    default FolderName toFolderName(String source) {
        return FolderName.of(source);
    }

    default FolderDescription toFolderDescription(String source){
        return FolderDescription.of(source);
    }

}

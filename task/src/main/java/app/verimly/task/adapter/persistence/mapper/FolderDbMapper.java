package app.verimly.task.adapter.persistence.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.core.domain.vo.Color;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.FolderEntity;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.vo.FolderDescription;
import app.verimly.task.domain.vo.FolderId;
import app.verimly.task.domain.vo.FolderName;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class})
public interface FolderDbMapper {


    FolderEntity toJpaEntity(Folder source);


    default Folder toDomainEntity(FolderEntity jpa) {
        if (jpa == null)
            return null;

        return Folder.reconstruct(
                FolderId.reconstruct(jpa.getId()),
                UserId.reconstruct(jpa.getOwnerId()),
                FolderName.reconstruct(jpa.getName()),
                FolderDescription.reconstruct(jpa.getDescription()),
                Color.reconstruct(jpa.getLabelColor())
        );
    }
}

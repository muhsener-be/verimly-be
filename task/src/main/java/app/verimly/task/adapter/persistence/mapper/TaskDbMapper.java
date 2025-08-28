package app.verimly.task.adapter.persistence.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.TaskEntity;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.DueDate;
import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskId;
import app.verimly.task.domain.vo.task.TaskName;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class})
public interface TaskDbMapper {


    TaskEntity toJpaEntity(Task task);

    default Task toDomainEntity(TaskEntity source) {
        if(source == null)
            return null;
        return Task.reconstruct(
                TaskId.reconstruct(source.getId()),
                UserId.reconstruct(source.getOwnerId()),
                FolderId.reconstruct(source.getFolderId()),
                TaskName.reconstruct(source.getName()),
                TaskDescription.reconstruct(source.getDescription()),
                DueDate.reconstruct(source.getDueDate()),
                source.getStatus(),
                source.getPriority()
        );
    }
}

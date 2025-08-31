package app.verimly.task.adapter.persistence.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.TaskEntity;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.application.dto.TaskWithSessionsData;
import app.verimly.task.application.ports.out.persistence.SessionSummaryProjection;
import app.verimly.task.application.ports.out.persistence.TaskSummaryProjection;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.DueDate;
import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskId;
import app.verimly.task.domain.vo.task.TaskName;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class})
public interface TaskDbMapper {

    CoreVoMapper coreVoMapper = Mappers.getMapper(CoreVoMapper.class);

    TaskEntity toJpaEntity(Task task);

    default Task toDomainEntity(TaskEntity source) {
        if (source == null)
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

    default void mergeFromDomain(Task from, TaskEntity to) {
        if (from == null || to == null)
            return;

        to.setName(coreVoMapper.fromVO(from.getName()));
        to.setDescription(coreVoMapper.fromVO(from.getDescription()));
        to.setStatus(from.getStatus());
        to.setPriority(from.getPriority());
        to.setDueDate(coreVoMapper.fromVO(from.getDueDate()));
        to.setOwnerId(coreVoMapper.fromVO(from.getOwnerId()));
        to.setFolderId(coreVoMapper.fromVO(from.getFolderId()));

    }

    TaskWithSessionsData toTaskWithSessionsData(TaskSummaryProjection task, List<SessionSummaryProjection> sessions);

    List<SessionSummaryData> toSessionSummaryData(List<SessionSummaryProjection> projections);

    SessionSummaryData toSessionSummaryData(SessionSummaryProjection projection);

}

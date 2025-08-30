package app.verimly.task.adapter.persistence.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.SessionEntity;
import app.verimly.task.application.mapper.TaskVoMapper;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.vo.session.SessionName;
import app.verimly.task.domain.vo.task.TaskId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class, TaskVoMapper.class})
public interface SessionDbMapper {


    SessionEntity toJpaEntity(TimeSession session);

    default TimeSession toDomainEntity(SessionEntity source) {
        if (source == null)
            return null;
        return TimeSession.reconstruct(
                SessionId.of(source.getId()),
                UserId.of(source.getOwnerId()),
                SessionName.reconstruct(source.getName()),
                TaskId.of(source.getTaskId()),
                source.getStartedAt(),
                source.getPausedAt(),
                source.getFinishedAt(),
                source.getTotalPause(),
                source.getStatus()
        );
    }

}

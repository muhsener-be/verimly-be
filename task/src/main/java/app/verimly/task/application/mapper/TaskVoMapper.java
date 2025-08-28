package app.verimly.task.application.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.task.domain.vo.folder.FolderDescription;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.folder.FolderName;
import app.verimly.task.domain.vo.task.*;
import org.mapstruct.Mapper;

import java.time.ZonedDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = CoreVoMapper.class)
public interface TaskVoMapper {


    default FolderName toFolderName(String source) {
        return FolderName.of(source);
    }

    default FolderDescription toFolderDescription(String source) {
        return FolderDescription.of(source);
    }

    default FolderId toFolderId(UUID value) {
        return FolderId.of(value);
    }

    default TaskName toTaskName(String value) {
        return TaskName.of(value);
    }

    default TaskDescription toTaskDescription(String value) {
        return TaskDescription.of(value);
    }

    default DueDate toDueDate(ZonedDateTime value) {
        if (value == null)
            return null;
        return DueDate.of(value.toInstant());
    }

    default Priority toPriority(String value) {
        return Priority.of(value);
    }

    default TaskStatus toTaskStatus(String value) {
        return TaskStatus.of(value);
    }
}

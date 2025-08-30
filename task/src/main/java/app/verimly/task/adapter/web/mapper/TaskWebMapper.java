package app.verimly.task.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.core.domain.mapper.ZonedTimeMapper;
import app.verimly.task.adapter.web.dto.request.CreateTaskWebRequest;
import app.verimly.task.adapter.web.dto.request.MoveTaskToFolderWebRequest;
import app.verimly.task.adapter.web.dto.request.ReplaceTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.TaskCreationWebResponse;
import app.verimly.task.adapter.web.dto.response.TaskSummaryWebResponse;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.mapper.TaskVoMapper;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.application.usecase.command.task.move_to_folder.MoveTaskToFolderCommand;
import app.verimly.task.application.usecase.command.task.replace.ReplaceTaskCommand;
import app.verimly.task.domain.vo.task.TaskId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {TaskVoMapper.class, CoreVoMapper.class, ZonedTimeMapper.class})
public interface TaskWebMapper {

    CreateTaskCommand toCreateTaskCommand(CreateTaskWebRequest request);


    TaskCreationWebResponse toTaskCreationWebResponse(TaskCreationResponse source);


    TaskSummaryWebResponse toTaskSummaryWebResponse(TaskSummaryData source);


    default List<TaskSummaryWebResponse> toTaskSummaryWebResponse(List<TaskSummaryData> source) {
        if (source == null)
            return null;

        return source.stream()
                .filter(Objects::nonNull)
                .map(this::toTaskSummaryWebResponse)
                .toList();

    }


    @Mapping(target = "folderId", source = "source.folderId")
    MoveTaskToFolderCommand toMoveTaskToFolderCommand(UUID taskId, MoveTaskToFolderWebRequest source);


    ReplaceTaskCommand toReplaceTaskCommand(ReplaceTaskWebRequest source, TaskId taskId);
}


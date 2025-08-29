package app.verimly.task.application.ports.out.persistence;

import app.verimly.task.domain.vo.task.Priority;
import app.verimly.task.domain.vo.task.TaskStatus;

import java.time.Instant;
import java.util.UUID;

public interface TaskDetailsProjection {

    UUID getId();

    UUID getOwnerId();

    UUID getFolderId();

    String getName();

    String getDescription();

    Instant getDueDate();

    TaskStatus getStatus();

    Priority getPriority();

    Instant getCreatedAt();

    Instant getUpdatedAt();
}

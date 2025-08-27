package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.vo.BaseId;

import java.util.UUID;

public class TaskId extends BaseId<UUID> {

    protected TaskId(UUID value) {
        super(value);
    }

    public static TaskId of(UUID value) {
        return value == null ? null : new TaskId(value);
    }

    public static TaskId random() {
        return new TaskId(UUID.randomUUID());
    }
}

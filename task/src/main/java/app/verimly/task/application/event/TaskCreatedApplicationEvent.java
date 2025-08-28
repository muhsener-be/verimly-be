package app.verimly.task.application.event;

import app.verimly.commons.core.application.event.ApplicationEvent;
import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.domain.entity.Task;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.Instant;


@Getter
public final class TaskCreatedApplicationEvent extends ApplicationEvent {
    private final Principal actor;
    private final Task task;

    public TaskCreatedApplicationEvent(@NotNull Principal actor, @NotNull Task task) {
        super(Instant.now());
        this.actor = actor;
        this.task = task;

        assertInputsAreNotNull();
    }

    private void assertInputsAreNotNull() {
        Assert.notNull(actor, "Actor cannot be null in TaskCreatedApplicationEvent");
        Assert.notNull(task, "Task cannot be null in TaskCreatedApplicationEvent");
    }


}

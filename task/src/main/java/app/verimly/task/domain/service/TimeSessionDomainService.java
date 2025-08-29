package app.verimly.task.domain.service;


import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.exception.TimeSessionDomainException;
import app.verimly.task.domain.input.SessionCreationDetails;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class TimeSessionDomainService {


    public TimeSession startSessionForTask(@NotNull Task task, @NotNull SessionCreationDetails sessionDetails) {
        Assert.notNull(task, "Task cannot be null to start session for task.");
        Assert.notNull(sessionDetails, "SessionCreationDetails cannot be null to start session for task.");

        ensureOwnersMatches(task.getOwnerId(), sessionDetails.ownerId());

        return TimeSession.startForTask(task.getId(), sessionDetails.name(), sessionDetails.ownerId());
    }

    private void ensureOwnersMatches(UserId taskOwnerId, UserId sessionOwnerId) {
        if (!Objects.equals(taskOwnerId, sessionOwnerId))
            throw new TimeSessionDomainException(TimeSession.Errors.TASK_OWNER_NOT_MATCH);
    }
}

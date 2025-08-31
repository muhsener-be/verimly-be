package app.verimly.task.application.ports.out.persistence;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public interface SessionSummaryProjection {

    UUID getId();

    UUID getOwnerId();

    UUID getTaskId();

    String getName();


    String getStatus();

    Instant getStartedAt();

    Instant getPausedAt();

    Instant getFinishedAt();

    Duration getTotalPause();


}

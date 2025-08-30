package app.verimly.task.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class SessionSummaryData {

    private UUID id;
    private UUID ownerId;
    private UUID taskId;
    private String name;
    private String status;
    private Instant startedAt;
    private Instant pausedAt;
    private Instant finishedAt;
    private Duration totalPause;


}

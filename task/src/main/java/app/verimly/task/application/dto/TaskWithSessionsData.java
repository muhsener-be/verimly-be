package app.verimly.task.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Builder
@AllArgsConstructor
@Getter
public class TaskWithSessionsData {

    private final UUID id;
    private final UUID ownerId;
    private final UUID folderId;
    private final String name;
    private final String description;
    private final Instant dueDate;
    private final String status;
    private final String priority;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final List<SessionSummaryData> sessions;
}

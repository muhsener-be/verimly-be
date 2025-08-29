package app.verimly.task.application.dto;


import java.time.Instant;
import java.util.UUID;

public record TaskDetailsData(
        UUID id, UUID ownerId, UUID folderId, String name, String description,
        Instant dueDate, String status, String priority, Instant createdAt,
        Instant updatedAt) {

}

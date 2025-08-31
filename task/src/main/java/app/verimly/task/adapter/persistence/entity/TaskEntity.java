package app.verimly.task.adapter.persistence.entity;

import app.verimly.commons.core.adapter.persistence.BaseJpaEntity;
import app.verimly.task.domain.vo.task.Priority;
import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskName;
import app.verimly.task.domain.vo.task.TaskStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE tasks SET is_deleted = true WHERE id = ?")
public class TaskEntity extends BaseJpaEntity<UUID> {

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "folder_id", nullable = false)
    private UUID folderId;

    @Column(name = "name", nullable = false, length = TaskName.MAX_LENGTH)
    private String name;

    @Column(name = "description", nullable = true, length = TaskDescription.MAX_LENGTH)
    private String description;

    @Column(name = "due_date", nullable = true)
    private Instant dueDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "priority", nullable = true)
    private Priority priority;




    @Builder(toBuilder = true)
    public TaskEntity(UUID id, UUID ownerId, UUID folderId, String name,
                      String description, Instant dueDate, TaskStatus status,
                      Priority priority) {
        this.id = id;
        this.ownerId = ownerId;
        this.folderId = folderId;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }
}

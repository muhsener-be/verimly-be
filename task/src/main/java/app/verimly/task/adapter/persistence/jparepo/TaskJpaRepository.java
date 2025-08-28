package app.verimly.task.adapter.persistence.jparepo;

import app.verimly.task.adapter.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {

    List<TaskEntity> findByFolderId(UUID folderId);
    List<TaskEntity> findByOwnerId(UUID ownerId);
}

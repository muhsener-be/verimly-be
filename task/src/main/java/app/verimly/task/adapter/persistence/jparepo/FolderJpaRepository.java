package app.verimly.task.adapter.persistence.jparepo;

import app.verimly.task.adapter.persistence.entity.FolderEntity;
import app.verimly.task.application.ports.out.persistence.FolderSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FolderJpaRepository extends JpaRepository<FolderEntity, UUID> {


    List<FolderSummaryProjection> findSummariesByOwnerId(UUID ownerId);


}


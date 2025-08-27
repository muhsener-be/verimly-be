package app.verimly.task.adapter.persistence.jparepo;

import app.verimly.task.adapter.persistence.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FolderJpaRepository extends JpaRepository<FolderEntity, UUID> {



}

package app.verimly.task.adapter.persistence.entity;

import app.verimly.commons.core.adapter.persistence.BaseJpaEntity;
import app.verimly.task.domain.vo.folder.FolderDescription;
import app.verimly.task.domain.vo.folder.FolderName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "folders")
@NoArgsConstructor
@Getter
@Setter
public class FolderEntity extends BaseJpaEntity<UUID> {

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "name", nullable = false, length = FolderName.MAX_LENGTH)
    private String name;

    @Column(name = "description", nullable = true, length = FolderDescription.MAX_LENGTH)
    private String description;

    @Column(name = "label_color", nullable = true)
    private String labelColor;

    @Builder
    public FolderEntity(UUID id, UUID ownerId, String name, String description, String labelColor) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.labelColor = labelColor;
    }
}

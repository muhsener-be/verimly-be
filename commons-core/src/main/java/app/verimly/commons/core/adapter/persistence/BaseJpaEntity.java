package app.verimly.commons.core.adapter.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@FilterDef(name = "soft-delete-filter", defaultCondition = "(is_deleted = false)")
@Filter(name = "soft-delete-filter")
public abstract class BaseJpaEntity<ID> {
    @Id
    @Setter
    protected ID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    protected Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    protected Instant updatedAt;

    @Column(name = "is_deleted")
    protected boolean deleted;
}

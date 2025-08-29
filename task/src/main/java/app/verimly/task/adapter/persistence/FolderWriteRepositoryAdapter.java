package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.FolderEntity;
import app.verimly.task.adapter.persistence.jparepo.FolderJpaRepository;
import app.verimly.task.adapter.persistence.mapper.FolderDbMapper;
import app.verimly.task.application.exception.FolderNotFoundException;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.vo.folder.FolderId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FolderWriteRepositoryAdapter implements FolderWriteRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final FolderDbMapper mapper;
    private final FolderJpaRepository folderJpaRepository;

    @Override
    @Transactional
    public Folder save(Folder folder) throws TaskDataAccessException {
        Assert.notNull(folder, "Folder cannot be null to save");

        try {
            FolderEntity jpaEntity = mapper.toJpaEntity(folder);
            entityManager.persist(jpaEntity);
            entityManager.flush();
            return folder;
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Folder> findById(FolderId id) throws TaskDataAccessException {
        Assert.notNull(id, "FolderId cannot be null to fetch by id");

        try {
            return Optional.ofNullable(entityManager.find(FolderEntity.class, id.getValue()))
                    .map(mapper::toDomainEntity);
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }

    }

    @Override
    public List<Folder> findByOwner(UserId userId) throws TaskDataAccessException {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserId> findOwnerOf(FolderId folderId) throws FolderNotFoundException, TaskDataAccessException {
        Assert.notNull(folderId, "folderId canot be null to find owner of the folder");

        try {
            TypedQuery<UUID> typedQuery = entityManager.createQuery("SELECT f.ownerId FROM FolderEntity f WHERE f.id = :id ", UUID.class)
                    .setParameter("id", folderId.getValue());

            UUID result = typedQuery.getSingleResult();
            return Optional.of(UserId.of(result));
        } catch (NoResultException nre) {
            return Optional.empty();
        } catch (Exception e) {
            throw new TaskDataAccessException(e.getMessage(), e);
        }


    }
}

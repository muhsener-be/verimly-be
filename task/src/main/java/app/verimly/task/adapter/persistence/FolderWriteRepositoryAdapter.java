package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskDataAccessException;
import app.verimly.task.domain.vo.FolderId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FolderWriteRepositoryAdapter implements FolderWriteRepository {
    @Override
    public Folder save(Folder folder) throws TaskDataAccessException {
        return null;
    }

    @Override
    public Optional<Folder> findById(FolderId id) throws TaskDataAccessException {
        return Optional.empty();
    }

    @Override
    public List<Folder> findByOwner(UserId userId) throws TaskDataAccessException {
        return List.of();
    }
}

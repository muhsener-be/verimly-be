package app.verimly.task.domain.repository;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.vo.FolderId;

import java.util.List;
import java.util.Optional;

public interface FolderWriteRepository {

    Folder save(Folder folder);

    Optional<Folder> findById(FolderId id);

    List<Folder> findByOwner(UserId userId);

}

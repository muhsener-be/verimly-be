package app.verimly.task.domain.repository;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.application.exception.FolderNotFoundException;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.vo.folder.FolderId;

import java.util.List;
import java.util.Optional;

public interface FolderWriteRepository {

    Folder save(Folder folder) throws TaskDataAccessException;

    Optional<Folder> findById(FolderId id) throws TaskDataAccessException;

    List<Folder> findByOwner(UserId userId) throws TaskDataAccessException;

    UserId findOwnerOf(FolderId folderId) throws FolderNotFoundException, TaskDataAccessException;
}

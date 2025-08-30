package app.verimly.task.adapter.persistence.entity;

import app.verimly.task.domain.entity.Folder;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TestEntitySetup {
    private UserEntity user;
    private TaskEntity task;
    private FolderEntity folder;



}

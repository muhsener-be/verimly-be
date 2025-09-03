package app.verimly.task.logging;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.folder.FolderName;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
public class FolderLog {

    public static void folderCreated(Actor actor, UserId ownerId, FolderId folderId, FolderName folderName) {
        log.info("New Folder created",
                kv("event", "folder.created"),
                kv("details", Map.of(
                        "owner_id", ownerId.toString(),
                        "actor", actor.toString(),
                        "folder_id", folderId.toString(),
                        "folder_name", folderName.toString()
                ))
        );
    }
}

package app.verimly.task.logging;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.task.TaskId;
import app.verimly.task.domain.vo.task.TaskName;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
public class TaskLog {


    public static void taskCreated(Actor actor, TaskId taskId, UserId ownerId, FolderId folderId, TaskName name) {
        log.info("New task created",
                kv("event", "task.created"),
                kv("details", Map.of(
                        "actor", actor.toString(),
                        "task_id", taskId.toString(),
                        "owner_id", ownerId.toString(),
                        "name", name.toString(),
                        "folder_id", folderId.toString()
                ))
        );
    }
}

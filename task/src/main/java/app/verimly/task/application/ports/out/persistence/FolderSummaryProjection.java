package app.verimly.task.application.ports.out.persistence;

import java.util.UUID;

public interface FolderSummaryProjection {

    UUID getId();

    UUID getOwnerId();

    String getName();

    String getLabelColor();

    String getDescription();

    // TODO: Add it after create task story
//    int taskCount();


}

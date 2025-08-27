package app.verimly.task.application.ports.out.persistence;

import java.util.UUID;

public interface FolderSummaryProjection {

    UUID getId();

    String getName();

    String getLabelColor();


    // TODO: Add it after create task story
//    int taskCount();


}

package app.verimly.task.application.ports.out.security.action;

import app.verimly.commons.core.security.Action;

public class TaskActions {

    public static final Action CREATE = new Action("TASK", "CREATE");
    public static final Action LIST_BY_FOLDER = new Action("TASK", "LIST_BY_FOLDER");
}

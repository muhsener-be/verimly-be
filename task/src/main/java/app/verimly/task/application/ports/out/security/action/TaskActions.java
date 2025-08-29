package app.verimly.task.application.ports.out.security.action;

import app.verimly.commons.core.security.Action;

public class TaskActions {

    public static final Action CREATE = new Action("TASK", "CREATE");
    public static final Action LIST_BY_FOLDER = new Action("TASK", "LIST_BY_FOLDER");
    public static final Action MOVE_TO_FOLDER = new Action("TASK", "MOVE_TO_FOLDER");
    public static final Action REPLACE = new Action("TASK", "REPLACE");
    public static final Action DELETE = new Action("TASK", "DELETE");
}

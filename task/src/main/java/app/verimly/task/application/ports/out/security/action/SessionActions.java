package app.verimly.task.application.ports.out.security.action;

import app.verimly.commons.core.security.Action;

public class SessionActions {

    public static final Action START = new Action("SESSION", "START");
    public static final Action CHANGE_STATUS = new Action("SESSION", "CHANGE_STATUS");
    public static final Action VIEW = new Action("SESSION", "VIEW");


}

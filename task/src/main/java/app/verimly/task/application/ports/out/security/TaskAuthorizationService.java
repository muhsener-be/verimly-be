package app.verimly.task.application.ports.out.security;

import app.verimly.commons.core.security.AuthorizationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.application.ports.out.security.context.*;

public interface TaskAuthorizationService extends AuthorizationService {


    void authorizeCreateFolder(Principal principal, CreateFolderContext context);

    void authorizeMoveToFolder(Principal principal, MoveToFolderContext context);

    void authorizeListFolders(Principal principal, ListFoldersContext context);

    void authorizeCreateTask(Principal principal, CreateTaskContext context);

    void authorizeListTasksByFolder(Principal principal, ListTasksByFolderContext context);

    void authorizeReplaceTask(Principal principal, ReplaceTaskContext context);

    void authorizeDeleteTask(Principal principal, DeleteTaskContext context);


    void authorizeStartSession(Principal principal, StartSessionContext context);

    void authorizeChangeSessionStatus(Principal principal, ChangeSessionStatusContext context);

    void authorizeViewTask(Principal principal, ViewTaskContext context);

    void authorizeViewSession(Principal principal, ViewSessionContext context);
}

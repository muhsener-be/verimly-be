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
}

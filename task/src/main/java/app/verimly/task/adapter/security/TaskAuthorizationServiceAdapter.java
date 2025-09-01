package app.verimly.task.adapter.security;

import app.verimly.commons.core.security.Principal;
import app.verimly.task.adapter.security.rules.*;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskAuthorizationServiceAdapter implements TaskAuthorizationService {

    private final MoveTaskToFolderAuthorizationRule moveTaskToFolderAuthorizationRule;
    private final ListFoldersAuthorizationRule listFoldersAuthorizationRule;
    private final CreateTaskAuthorizationRule createTaskAuthorizationRule;
    private final ListTasksByFolderAuthorizationRule listTasksByFolderAuthorizationRule;
    private final ReplaceTaskAuthorizationRule replaceTaskAuthorizationRule;
    private final DeleteTaskAuthorizationRule deleteTaskAuthorizationRule;
    private final StartSessionAuthorizationRule startSessionAuthorizationRule;
    private final ChangeSessionStatusAuthorizationRule changeSessionStatusAuthorizationRule;
    private final ViewTaskAuthorizationRule viewTaskAuthorizationRule;
    private final ViewSessionAuthorizationRule viewSessionAuthorizationRule;


    @Override
    public void authorizeCreateFolder(Principal principal, CreateFolderContext context) {

    }

    @Override
    public void authorizeMoveToFolder(Principal principal, MoveToFolderContext context) {
        moveTaskToFolderAuthorizationRule.apply(principal, context);
    }

    @Override
    public void authorizeListFolders(Principal principal, ListFoldersContext context) {
        listFoldersAuthorizationRule.apply(principal, context);
    }

    @Override
    public void authorizeCreateTask(Principal principal, CreateTaskContext context) {
        createTaskAuthorizationRule.apply(principal, context);
        ;
    }

    @Override
    public void authorizeListTasksByFolder(Principal principal, ListTasksByFolderContext context) {
        listTasksByFolderAuthorizationRule.apply(principal, context);
    }

    @Override
    public void authorizeReplaceTask(Principal principal, ReplaceTaskContext context) {
        replaceTaskAuthorizationRule.apply(principal, context);
    }

    @Override
    public void authorizeDeleteTask(Principal principal, DeleteTaskContext context) {
        deleteTaskAuthorizationRule.apply(principal, context);
    }

    @Override
    public void authorizeStartSession(Principal principal, StartSessionContext context) {
        startSessionAuthorizationRule.apply(principal, context);
    }

    @Override
    public void authorizeChangeSessionStatus(Principal principal, ChangeSessionStatusContext context) {
        changeSessionStatusAuthorizationRule.apply(principal, context);
    }

    @Override
    public void authorizeViewTask(Principal principal, ViewTaskContext context) {
        viewTaskAuthorizationRule.apply(principal, context);
    }

    @Override
    public void authorizeViewSession(Principal principal, ViewSessionContext context) {
        viewSessionAuthorizationRule.apply(principal,context);
    }
}

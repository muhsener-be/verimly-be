package app.verimly.task.application.ports.in;

import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;

public interface TaskApplicationService {

    TaskCreationResponse create(CreateTaskCommand command);
}

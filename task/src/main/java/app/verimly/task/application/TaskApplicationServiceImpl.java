package app.verimly.task.application;

import app.verimly.task.application.ports.in.TaskApplicationService;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommandHandler;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskApplicationServiceImpl implements TaskApplicationService {

    private final CreateTaskCommandHandler createTaskCommandHandler;

    @Override
    public TaskCreationResponse create(CreateTaskCommand command) {
        return createTaskCommandHandler.handle(command);
    }
}

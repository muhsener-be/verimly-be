package app.verimly.task.application.exception;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.NotFoundException;
import app.verimly.task.domain.vo.task.TaskId;

public class TaskNotFoundException extends NotFoundException {

    public static ErrorMessage ERROR_MESSAGE = ErrorMessage.of("task.not-found", "Task not found.");

    public TaskNotFoundException(TaskId taskId) {
        super(ERROR_MESSAGE.withDefaultMessage("Task not found with provided ID: %s".formatted(taskId)));
    }
}

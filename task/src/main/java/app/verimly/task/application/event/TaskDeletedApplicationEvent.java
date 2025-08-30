package app.verimly.task.application.event;


import app.verimly.task.domain.entity.Task;
import lombok.Getter;

public record TaskDeletedApplicationEvent(Task deletedTask) {
}

package app.verimly.task.application.event.listener;

import app.verimly.task.application.event.TaskDeletedApplicationEvent;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.repository.TimeSessionWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskDeletedListener {

    private final TimeSessionWriteRepository sessionRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = TaskDeletedApplicationEvent.class)
    public void onTaskDeleted(TaskDeletedApplicationEvent event) {
        Task task = event.deletedTask();
        List<TimeSession> timeSessions = sessionRepository.deleteAllByTaskId(task.getId());

        log.debug("Task: {}, and its session  deleted: {}", task.getId(), timeSessions.stream().map(TimeSession::getId).toList());
    }
}

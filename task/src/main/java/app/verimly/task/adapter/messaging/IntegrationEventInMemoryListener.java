package app.verimly.task.adapter.messaging;

import app.verimly.commons.event.UserCreatedMessage;
import app.verimly.task.adapter.messaging.mapper.TaskMessagingMapper;
import app.verimly.task.application.ports.in.messaging.CreatedUserDetails;
import app.verimly.task.application.ports.in.messaging.UserCreatedEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IntegrationEventInMemoryListener implements IntegrationEventListener {

    private final TaskMessagingMapper mapper;
    private final UserCreatedEventHandler handler;


    @Async
    @EventListener(classes = UserCreatedMessage.class)
    public void onUserCreated(UserCreatedMessage message) {
        log.info("Listening to UserCreatedMessage in task module. Message: {}", message);
        CreatedUserDetails details = mapper.toCreatedUserDetails(message);
        handler.handle(details);
    }
}

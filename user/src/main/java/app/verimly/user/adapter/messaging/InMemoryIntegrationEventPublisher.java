package app.verimly.user.adapter.messaging;

import app.verimly.commons.event.UserCreatedMessage;
import app.verimly.user.adapter.messaging.mapper.UserMessagingMapper;
import app.verimly.user.application.event.UserCreatedApplicationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryIntegrationEventPublisher implements IntegrationEventPublisher {

    private final UserMessagingMapper mapper;
    private final ApplicationEventPublisher publisher;


    private void publish(Object event) {
        publisher.publishEvent(event);
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = {UserCreatedApplicationEvent.class})
    public void onUserCreated(UserCreatedApplicationEvent event) {
        UserCreatedMessage message = mapper.toUserCreatedMessage(event);
        log.info("After commit, publishing UserCreatedMessage with in-memory publisher. Message:  {}", message.toString());
        publish(message);
    }
}

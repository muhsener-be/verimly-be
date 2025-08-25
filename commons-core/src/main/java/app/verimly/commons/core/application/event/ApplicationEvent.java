package app.verimly.commons.core.application.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class ApplicationEvent {
    private final Instant occurredAt;

    public ApplicationEvent(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }


}

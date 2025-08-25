package app.verimly.commons.core.domain.entity;

import app.verimly.commons.core.domain.event.DomainEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class BaseEntity<ID> {

    @Getter
    protected ID id;

    private List<DomainEvent> domainEvents = new ArrayList<>();

    protected void addDomainEvent(DomainEvent domainEvent) {
        this.domainEvents.add(domainEvent);
    }


    public List<DomainEvent> pullDomainEvents() {
        ArrayList<DomainEvent> copyOfDomainEvents = new ArrayList<>(domainEvents);
        this.domainEvents.clear();
        return copyOfDomainEvents;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

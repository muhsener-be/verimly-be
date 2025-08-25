package app.verimly.commons.core.domain.entity;

import app.verimly.commons.core.domain.event.DomainEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for all domain entities.
 * <p>
 * Provides identity management, domain event handling, and equality logic for entities.
 * </p>
 *
 * @param <ID> the type of the entity identifier
 */
public abstract class BaseEntity<ID> {

    /**
     * The unique identifier of the entity.
     */
    @Getter
    protected ID id;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * Adds a domain event to the entity's event list.
     *
     * @param domainEvent the domain event to add
     */
    protected void addDomainEvent(DomainEvent domainEvent) {
        this.domainEvents.add(domainEvent);
    }

    /**
     * Returns and clears all domain events associated with this entity.
     *
     * @return a list of domain events
     */
    public List<DomainEvent> pullDomainEvents() {
        ArrayList<DomainEvent> copyOfDomainEvents = new ArrayList<>(domainEvents);
        this.domainEvents.clear();
        return copyOfDomainEvents;
    }

    /**
     * Checks equality based on the entity's identifier and class type.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Returns the hash code based on the entity's identifier.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

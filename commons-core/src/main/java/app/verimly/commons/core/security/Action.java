package app.verimly.commons.core.security;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class Action {
    private final String resourceName;
    private final String action;
    private final String display;
    private final String resourceId;

    public Action(String resourceName, String action) {
        this(resourceName, action, null);
    }

    public Action(String resourceName, String action, String display) {
        this(resourceName, action, display, null);

    }

    public Action(String resourceName, String action, String display, String resourceId) {
        this.resourceName = resourceName;
        this.action = action;
        this.display = display;
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return display;
    }

    public String resourceName() {
        return resourceName;
    }

    public String action() {
        return action;
    }

    public String display() {
        return display;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Action) obj;
        return Objects.equals(this.resourceName, that.resourceName) &&
                Objects.equals(this.action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceName, action);
    }


}

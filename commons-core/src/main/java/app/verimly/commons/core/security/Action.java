package app.verimly.commons.core.security;

public record Action(String resourceName, String action) {

    @Override
    public String toString() {
        return action + " " + resourceName;
    }
}

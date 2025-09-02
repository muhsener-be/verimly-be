package app.verimly.task.application.ports.in.messaging;

public interface UserCreatedEventHandler {


    void handle(CreatedUserDetails details);
}

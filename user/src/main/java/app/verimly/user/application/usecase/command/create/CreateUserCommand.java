package app.verimly.user.application.usecase.command.create;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.user.domain.vo.Password;
import app.verimly.user.domain.vo.PersonName;
import lombok.Builder;


public record CreateUserCommand(PersonName name, Email email, Password password) {

    @Builder
    public static CreateUserCommand of(PersonName name, Email email, Password password) {
        return new CreateUserCommand(name, email, password);
    }
}

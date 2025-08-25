package app.verimly.user.application.usecase.command.create;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.domain.vo.PersonName;
import lombok.Builder;


public record UserCreationResponse(UserId id, PersonName name, Email email) {

    @Builder
    public static UserCreationResponse of(UserId id, PersonName name, Email email) {
        return new UserCreationResponse(id, name, email);
    }
}

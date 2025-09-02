package app.verimly.task.application.ports.in.messaging;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;

public record CreatedUserDetails(UserId id, Email email) {


}

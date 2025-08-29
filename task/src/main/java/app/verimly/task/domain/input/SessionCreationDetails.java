package app.verimly.task.domain.input;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.vo.session.SessionName;

public record SessionCreationDetails(SessionName name, UserId ownerId) {
}

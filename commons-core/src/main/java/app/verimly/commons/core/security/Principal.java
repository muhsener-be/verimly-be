package app.verimly.commons.core.security;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;

public interface Principal {

    UserId getId();

    Email getEmail();
}

package app.verimly.user.application.ports.out.persistence;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.application.dto.UserDetailsData;

import java.util.Optional;

public interface UserReadRepository {


    Optional<UserDetailsData> fetchUserDetailsById(UserId id);
}

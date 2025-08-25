package app.verimly.user.domain.repository;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.domain.entity.User;

import java.util.Optional;

public interface UserWriteRepository {

    User save(User user);

    Optional<User> findById(UserId id);
}

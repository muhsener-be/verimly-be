package app.verimly.user.domain.repository;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.domain.entity.User;

import java.util.Optional;

public interface UserWriteRepository {

    User save(User user) throws UserDataAccessException;

    Optional<User> findById(UserId id) throws UserDataAccessException;

    boolean existsByEmail(Email email);
}

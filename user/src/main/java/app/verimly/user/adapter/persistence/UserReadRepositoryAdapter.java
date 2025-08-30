package app.verimly.user.adapter.persistence;

import app.verimly.commons.core.adapter.persistence.aspect.EnableSoftDeleteFilter;
import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.adapter.persistence.jparepo.UserJpaRepository;
import app.verimly.user.application.dto.UserDetailsData;
import app.verimly.user.application.ports.out.persistence.UserReadRepository;
import app.verimly.user.domain.repository.UserDataAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserReadRepositoryAdapter implements UserReadRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    @Transactional
    @EnableSoftDeleteFilter
    public Optional<UserDetailsData> fetchUserDetailsById(UserId id) {
        Assert.notNull(id, "Id cannot be null to fetch user details");
        try {
            return jpaRepository.fetchDetailsDataById(id.getValue());
        } catch (Exception e) {
            throw new UserDataAccessException(e.getMessage(), e);
        }
    }
}

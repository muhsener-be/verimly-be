package app.verimly.user.adapter.persistence;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.adapter.persistence.mapper.UserDbMapper;
import app.verimly.user.application.exception.DuplicateEmailException;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.repository.UserDataAccessException;
import app.verimly.user.domain.repository.UserWriteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserWriteRepositoryAdapter implements UserWriteRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final UserDbMapper mapper;

    @Override
    @Transactional
    public User save(User user) throws UserDataAccessException, DuplicateEmailException {
        try {
            Assert.notNull(user, "User to persist cannot be null!");

            UserEntity jpa = mapper.toJpaEntity(user);
            entityManager.persist(jpa);
            entityManager.flush();
            return user;
        } catch (ConstraintViolationException cve) {
            var kind = cve.getKind();

            if (kind.equals(ConstraintViolationException.ConstraintKind.UNIQUE)) {
                String constraintName = cve.getConstraintName();
                if (constraintName != null && isUserEmailUniqueConstraint(constraintName))
                    throw new DuplicateEmailException(user.getEmail());
            }

            throw new UserDataAccessException(cve.getMessage(), cve);


        } catch (Exception e) {
            throw new UserDataAccessException(e.getMessage(), e);
        }
    }

    private boolean isUserEmailUniqueConstraint(String constraintName) {
        assert constraintName != null : "null constraint name.";
        return constraintName.toLowerCase(Locale.US).contains(UserEntity.EMAIL_UNIQUE_CONSTRAINT_NAME);
    }


    @Override
    @Transactional
    public Optional<User> findById(UserId id) throws UserDataAccessException {
        try {
            Assert.notNull(id, "UserId cannot be null to find by id");
            UUID userUUID = id.getValue();

            return Optional.ofNullable(entityManager.find(UserEntity.class, userUUID))
                    .map(mapper::toDomainEntity);

        } catch (Exception e) {
            throw new UserDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByEmail(Email email) {
        return false;
    }
}

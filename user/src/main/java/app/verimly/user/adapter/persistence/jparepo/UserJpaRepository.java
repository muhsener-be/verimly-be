package app.verimly.user.adapter.persistence.jparepo;

import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.application.dto.UserDetailsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;


public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    @Query("""
            SELECT new app.verimly.user.application.dto.UserDetailsData(
                        u.id, u.firstName,u.lastName , u.email, u.createdAt, u.updatedAt) 
                         FROM UserEntity u 
                                    WHERE u.id = :id """)
    Optional<UserDetailsData> fetchDetailsDataById(UUID id);
}

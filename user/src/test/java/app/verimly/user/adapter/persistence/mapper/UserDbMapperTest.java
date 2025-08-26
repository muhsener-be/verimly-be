package app.verimly.user.adapter.persistence.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {UserDbMapperImpl.class, CoreVoMapperImpl.class})
class UserDbMapperTest {
    UserTestData DATA = UserTestData.getInstance();
    User user;

    @Autowired
    UserDbMapper mapper;


    @BeforeEach
    void setup() {
        user = DATA.user();

    }

    @Test
    void assert_setup_is_ok() {
        assertNotNull(mapper);
    }

    @Test
    public void toJpaEntity_whenValidUser_thenMapsToJpaEntity() {
        UserEntity jpaEntity = mapper.toJpaEntity(user);

        assertNotNull(jpaEntity);
        assertEquals(user.getId().getValue(), jpaEntity.getId());
        assertEquals(user.getEmail().getValue(), jpaEntity.getEmail());
        assertEquals(user.getPassword().getEncrypted(), jpaEntity.getPassword());
        assertEquals(user.getName().getFirstName(), jpaEntity.getFirstName());
        assertEquals(user.getName().getLastName(), jpaEntity.getLastName());
    }


    @Test
    public void toDomainEntity_whenValidJpa_thenMapsToDomainEntity() {
        UserEntity jpaEntity = mapper.toJpaEntity(user);


        User actual = mapper.toDomainEntity(jpaEntity);

        assertEquals(jpaEntity.getEmail(), actual.getEmail().getValue());
        assertEquals(jpaEntity.getFirstName(), actual.getName().getFirstName());
        assertEquals(jpaEntity.getLastName(), actual.getName().getLastName());
        assertEquals(jpaEntity.getId(), actual.getId().getValue());
        assertEquals(jpaEntity.getPassword(), actual.getPassword().getEncrypted());


    }

}
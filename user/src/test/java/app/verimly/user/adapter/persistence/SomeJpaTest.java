package app.verimly.user.adapter.persistence;

import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.data.UserTestData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SomeJpaTest {

    UserTestData DATA = UserTestData.getInstance();
    UserEntity entity;


    @BeforeEach
    public void setup() {
        entity = DATA.userEntity();
    }


    @PersistenceContext
    EntityManager em;

}

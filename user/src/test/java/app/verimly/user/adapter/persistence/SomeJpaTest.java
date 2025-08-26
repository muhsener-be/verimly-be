package app.verimly.user.adapter.persistence;

import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.data.UserTestData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    UserTestData DATA = UserTestData.getInstance();
    UserEntity entity;


    @BeforeEach
    public void setup() {
        entity = DATA.userEntity();
    }


    @PersistenceContext
    EntityManager em;

    @Test
    void persist_givenNullId_thenIdentifierGenerationException() {
        // Arrange
        entity.setId(null);

        // Act
        Executable action = () -> em.persist(entity);

        // Assert
        assertThrows(IdentifierGenerationException.class, action);
    }

    @Test
    void persist_givenNullFirstName_thenThrows() {
        // Arrange
        em.persist(entity);
        em.flush();
        UserEntity anotherEntity = DATA.userEntity();
        anotherEntity.setEmail(entity.getEmail());

        // Act
        try {
            em.persist(anotherEntity);
            em.flush();
        } catch (ConstraintViolationException e) {
            ConstraintViolationException.ConstraintKind kind = e.getKind();
            if (kind.equals(ConstraintViolationException.ConstraintKind.UNIQUE))
                if (e.getConstraintName().toLowerCase().contains(UserEntity.EMAIL_UNIQUE_CONSTRAINT_NAME))
                    System.out.println("OKKAYY");
        }


        // Assert


    }
}

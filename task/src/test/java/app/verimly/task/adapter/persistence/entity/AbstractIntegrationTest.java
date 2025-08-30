package app.verimly.task.adapter.persistence.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.defer-datasource-initialization=true"
})
public abstract class AbstractIntegrationTest {


    @PersistenceContext
    protected EntityManager entityManager;


    protected Executable getPersistAndFlushExecutable() {
        return this::persistAndFlush;
    }

    abstract protected void persistAndFlush();

    protected void assertThrowsException(Class<? extends Throwable> exClass) {
        assertThrows(exClass, getPersistAndFlushExecutable());
    }


    protected void assertThrowsConstraintViolationException() {
        assertThrowsException(ConstraintViolationException.class);
    }

    protected void assertThrowsDataException() {
        assertThrowsException(DataException.class);
    }


    protected void assertDoesNotThrowException() {
        assertDoesNotThrow(getPersistAndFlushExecutable());
    }


}

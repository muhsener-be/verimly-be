package app.verimly.user.adapter.persistence;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.adapter.persistence.jparepo.UserJpaRepository;
import app.verimly.user.adapter.persistence.mapper.UserDbMapper;
import app.verimly.user.application.exception.DuplicateEmailException;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.repository.UserDataAccessException;
import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UserWriteRepositoryAdapter Unit Tests")
class UserWriteRepositoryAdapterUnitTest {

    UserTestData DATA = UserTestData.getInstance();

    User user;
    UserEntity userEntity;
    UserId userId;
    Email email;

    @Mock
    UserDbMapper userDbMapper;

    @Mock
    EntityManager entityManager;
    @Mock
    UserJpaRepository userJpaRepository;

    @InjectMocks
    UserWriteRepositoryAdapter adapter;


    @BeforeEach
    @DisplayName("Setup is OK")
    public void setup() {
        user = DATA.user();
        userEntity = DATA.userEntity();
        userId = DATA.userId();
        email = DATA.email();

        lenient().when(userDbMapper.toJpaEntity(user)).thenReturn(userEntity);
        lenient().when(userDbMapper.toDomainEntity(userEntity)).thenReturn(user);
        lenient().when(userJpaRepository.findByEmail(email.getValue())).thenReturn(Optional.of(userEntity));
        lenient().doNothing().when(entityManager).persist(userEntity);
        lenient().doNothing().when(entityManager).flush();

    }

    @Test
    void setup_is_ok() {
        assertNotNull(userDbMapper);
        assertNotNull(entityManager);
        assertNotNull(adapter);
        assertNotNull(userJpaRepository);


    }

    @Nested
    @DisplayName("Test of save()")
    class saveTests {

        @Test
        @DisplayName("Happy Path")
        void save_whenValidUser_thenSavesAndReturnsUser() {
            //ACT
            User savedUser = adapter.save(user);

            //ASSERT
            assertEquals(user, savedUser);
            verify(userDbMapper).toJpaEntity(user);
            verify(entityManager).persist(userEntity);
            verify(entityManager).flush();

        }

        @Test
        @DisplayName("Null user -> IllegalArgumentException")
        void save_whenNullUser_thenThrowsIllegalArgumentException() {
            // Arrange
            user = null;

            // Act
            Executable executable = () -> adapter.save(user);

            // Assert
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);

        }

        @Test
        @DisplayName("Persist problem ->  UserDataAccessException")
        void save_whenProblemOccursDuringPersist_thenThrowsUserDataAccessException() {
            // Arrange
            RuntimeException anException = new RuntimeException("An exception message.");
            doThrow(anException).when(entityManager).persist(userEntity);

            // Act
            Executable executable = () -> adapter.save(user);

            // Assert
            UserDataAccessException exception = assertThrows(UserDataAccessException.class, executable);
            Throwable cause = exception.getCause();
            assertEquals(anException, cause);
        }

        @Test
        @DisplayName("Unique Email Problem ->  DuplicateEmailException")
        void save_whenThrowsConstraintViolationExceptionForEmailUniqueness_thenConvertsToDuplicateEmailException() {
            ConstraintViolationException violationException = Mockito.mock(ConstraintViolationException.class);
            when(violationException.getConstraintName()).thenReturn(UserEntity.EMAIL_UNIQUE_CONSTRAINT_NAME);
            doThrow(violationException).when(entityManager).flush();

            DuplicateEmailException exception = assertThrows(DuplicateEmailException.class, () -> adapter.save(user));


            assertEquals(user.getEmail(), exception.getEmail());
        }
    }


    @Nested
    @DisplayName("Test of findById()")
    class findByIdTests {

        @Test
        @DisplayName("Null UserId -> IllegalArgumentException")
        void findById_whenNullUserId_thenThrowsIllegalArgumentExceptiom() {
            // Arrange
            userId = null;

            //ACT
            Executable executable = () -> adapter.findById(userId);

            //Assert
            assertThrows(IllegalArgumentException.class, executable);


        }


        @Test
        @DisplayName("User not exists -> Empty Optional")
        void findById_whenUserNotExists_thenReturnEmptyOptional() {
            // Arrange
            UUID userUUID = userId.getValue();
            when(entityManager.find(UserEntity.class, userUUID)).thenReturn(null);

            //ACT
            Optional<User> actualOptional = adapter.findById(userId);

            //Assert
            assertTrue(actualOptional.isEmpty());
        }

        @Test
        @DisplayName("Happy Path")
        void findById_whenValidUserId_thenReturnsUser() {
            // Arrange
            when(entityManager.find(UserEntity.class, userId.getValue())).thenReturn(userEntity);
            when(userDbMapper.toDomainEntity(userEntity)).thenReturn(user);

            //Act
            Optional<User> actualOptional = adapter.findById(userId);
            assertTrue(actualOptional.isPresent());
            User actualUser = actualOptional.get();
            assertEquals(user, actualUser);
        }

        @Test
        @DisplayName("DB Problem -> UserDataAccessException")
        void findById_whenProblemOccursDuringFetching_thenThrowsUserDataAccessException() {
            // Arrange
            RuntimeException anException = new RuntimeException("An Exception");
            doThrow(anException).when(entityManager).find(UserEntity.class, userId.getValue());

            // Act
            Executable executable = () -> adapter.findById(userId);

            // Assert
            UserDataAccessException exception = assertThrows(UserDataAccessException.class, executable);
            assertEquals(anException, exception.getCause());

        }
    }


    @Nested
    @DisplayName("Test of findByEmail()")
    class findByEmailTests {

        @Test
        @DisplayName("Happy path")
        void findByEmail_whenValidEmail_thenReturnsUser() {

            Optional<User> optional = adapter.findByEmail(email);

            assertTrue(optional.isPresent());
            User found = optional.get();
            assertEquals(user, found);

        }

        @Test
        @DisplayName("Null Email -> IllegalArgumentException")
        void findByEmail_whenNullEmail_thenThrowsIllegalArgumentException() {
            email = null;

            Executable action = () -> adapter.findByEmail(email);

            assertThrows(IllegalArgumentException.class, action);

        }

        @Test
        @DisplayName("User Not Found  --> Empty Optional")
        void findByEmail_whenNotFound_thenReturnsEmptyOptional() {
            when(userJpaRepository.findByEmail(email.getValue())).thenReturn(Optional.empty());

            Optional<User> optional = adapter.findByEmail(email);
            assertTrue(optional.isEmpty());
        }

        @Test
        @DisplayName("DB Problem -> UserDataAccessException")
        void findByEmail_whenProblemDuringFetching_thenThrowsUserDataAccessException() {
            RuntimeException anException = new RuntimeException("An Exception");
            doThrow(anException).when(userJpaRepository).findByEmail(email.getValue());

            Executable action = () -> adapter.findByEmail(email);

            assertThrows(UserDataAccessException.class, action);

        }


    }
}
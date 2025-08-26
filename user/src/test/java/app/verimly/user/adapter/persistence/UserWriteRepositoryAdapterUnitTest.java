package app.verimly.user.adapter.persistence;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.adapter.persistence.jparepo.UserJpaRepository;
import app.verimly.user.adapter.persistence.mapper.UserDbMapper;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.repository.UserDataAccessException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
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
    @DisplayName("Save Tests")
    class saveTests {
        @Test
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
        void save_whenNullUser_thenThrowsUserDataAccessException() {
            // Arrange
            user = null;

            // Act
            Executable executable = () -> adapter.save(user);

            // Assert
            UserDataAccessException exception = assertThrows(UserDataAccessException.class, executable);
            Throwable cause = exception.getCause();
            assertInstanceOf(IllegalArgumentException.class, cause);
        }

        @Test
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
    }


    @Nested
    @DisplayName("findById Tests")
    class findByIdTests {
        @Test
        void findById_whenNullUserId_thenThrowsUserDataAccessException() {
            // Arrange
            userId = null;

            //ACT
            Executable executable = () -> adapter.findById(userId);

            //Assert
            assertThrows(IllegalArgumentException.class, executable);


        }


        @Test
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
    @DisplayName("findByEmail Tests")
    class findByEmailTests {

        @Test
        void findByEmail_whenValidEmail_thenReturnsUser() {

            Optional<User> optional = adapter.findByEmail(email);

            assertTrue(optional.isPresent());
            User found = optional.get();
            assertEquals(user, found);

        }

        @Test
        void findByEmail_whenNullEmail_thenThrowsIllegalArgumentException() {
            email = null;

            Executable action = () -> adapter.findByEmail(email);

            assertThrows(IllegalArgumentException.class, action);

        }

        @Test
        void findByEmail_whenNotFound_thenReturnsEmptyOptional() {
            when(userJpaRepository.findByEmail(email.getValue())).thenReturn(Optional.empty());

            Optional<User> optional = adapter.findByEmail(email);
            assertTrue(optional.isEmpty());
        }

        @Test
        void findByEmail_whenProblemDuringFetching_thenThrowsUserDataAccessException() {
            RuntimeException anException = new RuntimeException("An Exception");
            doThrow(anException).when(userJpaRepository).findByEmail(email.getValue());

            Executable action = () -> adapter.findByEmail(email);

            assertThrows(UserDataAccessException.class, action);

        }


    }
}
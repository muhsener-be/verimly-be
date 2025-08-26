package app.verimly.user.adapter.persistence;

import app.verimly.JpaTestConfig;
import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.adapter.persistence.mapper.UserDbMapper;
import app.verimly.user.adapter.persistence.mapper.UserDbMapperImpl;
import app.verimly.user.application.exception.DuplicateEmailException;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.entity.User;
import com.sun.jdi.request.DuplicateRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({UserWriteRepositoryAdapter.class, UserDbMapperImpl.class, CoreVoMapperImpl.class, JpaTestConfig.class})
public class UserWriteRepositoryAdapterIT {
    UserTestData DATA = UserTestData.getInstance();

    User user;

    @Autowired
    UserWriteRepositoryAdapter adapter;
    @Autowired
    UserDbMapper userDbMapper;

    @BeforeEach
    public void setup() {
        user = DATA.user();
    }

    @Test
    void setup_is_ok() {
        assertNotNull(adapter);
        assertNotNull(userDbMapper);

    }

    @Test
    public void save_whenValidUser_shouldSave() {
        UserId userId = user.getId();

        User saved = adapter.save(user);

        Optional<User> opt = adapter.findById(userId);
        assertTrue(opt.isPresent());
        User found = opt.get();

        assertEquals(found, saved);
        assertEquals(found.getId(), saved.getId());
        assertEquals(found.getName(), saved.getName());
        assertEquals(found.getEmail(), saved.getEmail());
        assertEquals(found.getPassword(), saved.getPassword());
    }

    @Test
    public void save_whenDuplicatedEmail_thenThrowsDuplicateEmailException() {
        // Arrange
        adapter.save(user);
        User anotherUserWithSameEmail = DATA.user();

        // Act
        Executable action = () -> adapter.save(anotherUserWithSameEmail);

        //Assert
        DuplicateEmailException exception = assertThrows(DuplicateEmailException.class, action);
        assertEquals(anotherUserWithSameEmail.getEmail(), exception.getEmail());
    }


}

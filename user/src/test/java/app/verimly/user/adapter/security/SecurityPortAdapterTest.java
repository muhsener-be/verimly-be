package app.verimly.user.adapter.security;

import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.vo.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SecurityPortAdapter.class, SecurityPortAdapterTest.Config.class})
class SecurityPortAdapterTest {

    UserTestData DATA = UserTestData.getInstance();
    Password password;

    @Autowired
    SecurityPortAdapter adapter;

    @BeforeEach
    public void setup() {
        password = DATA.password().raw();
    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(adapter);
    }

    @Test
    void encrypt_whenNullPassword_thenThrowsIllegalArgumentException() {
        password = null;

        Executable action = () -> adapter.encrypt(password);

        assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    void encrypy_whenValidPassword_thenEncryptPassword() {
        assertNotNull(password.getRaw());
        assertNotEquals("" , password.getRaw());
        assertNull(password.getEncrypted());


        Password encoded = adapter.encrypt(password);

        assertNotNull(encoded.getEncrypted());
        assertEquals("" , encoded.getRaw());
    }




    @TestConfiguration
    static class Config {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }
}
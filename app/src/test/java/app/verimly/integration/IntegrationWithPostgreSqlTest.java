package app.verimly.integration;

import app.verimly.bootstrap.BootstrapUserProps;
import app.verimly.composite.security.config.SecurityProperties;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class IntegrationWithPostgreSqlTest {


    @Autowired
    protected BootstrapUserProps userProps;

    @Autowired
    protected SecurityProperties securityProperties;

    @Autowired
    protected MockMvc mockMvc;

    protected MockCookie loginCookie;

    @BeforeEach
    void setup() throws Exception {
        if(loginCookie == null){
            loginCookie = loginAndGetCookie(userProps.getEmail(),userProps.getPassword());
        }
    }

    protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    protected MockCookie loginAndGetCookie(String email, String password) throws Exception {
        MvcResult result = mockMvc.perform(post(securityProperties.getLoginPath())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param(securityProperties.getUsernameParameter(), email)
                        .param(securityProperties.getPasswordParameter(), password))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie(securityProperties.getAccessTokenCookie().getName());
        assertNotNull(cookie);

        return (MockCookie) cookie;
    }

}

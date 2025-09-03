package app.verimly.integration;

import app.verimly.bootstrap.BootstrapUserProps;
import app.verimly.composite.security.config.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
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
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseIntegrationTest {


    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

    }

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected BootstrapUserProps bootstrapUserProps;

    @Autowired
    protected SecurityProperties securityProperties;

    protected static MockCookie loginCookie;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeAll
    void beforeAll() throws Exception {
        if (loginCookie == null) {
            System.out.println("Kaç kez çalıştı?");
            loginCookie = loginAndGetCookie(bootstrapUserProps.getEmail(), bootstrapUserProps.getPassword());
        }
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

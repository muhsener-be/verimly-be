package app.verimly.composite.security;

import app.verimly.composite.security.config.AccessTokenCookieProperties;
import app.verimly.composite.security.config.SecurityProperties;
import app.verimly.composite.security.cookie.CookieHelper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class AuthIntegrationTest {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AccessTokenCookieProperties cookieProperties;


    @Value("${bootstrap.user.email}")
    private String email;
    @Value("${bootstrap.user.password}")
    private String password;


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void login_success() throws Exception {
        MvcResult result = performLogin()
                .andExpect(status().isOk())
                .andReturn();

        Cookie cookie = result.getResponse().getCookie(cookieProperties.getName());
        assertNotNull(cookie);
    }


    @Test
    public void login_whenPasswordWrong_thenReturns401() throws Exception {
        password = "wrong-password";
        performLogin().
                andExpect(status().isUnauthorized());

    }

    @Test
    public void login_whenEmailWrong_thenReturns401() throws Exception {
        email = "wrong-email@email.com";
        performLogin().
                andExpect(status().isUnauthorized());

    }

    @Test
    public void logout_whenAuthenticated_shouldReturnLogoutCookie() throws Exception {
        MockCookie validAccessTokenCookie = loginAndGetCookie();
        MvcResult mvcResult = performLogout(validAccessTokenCookie).andReturn();
        MockCookie logoutCookie = ((MockCookie) mvcResult.getResponse().getCookie(cookieProperties.getName()));

        assertNotNull(logoutCookie);
        String value = logoutCookie.getValue();
        assertEquals("" , value);
        assertEquals(0, logoutCookie.getMaxAge());

    }

    @Test
    public void logout_whenFailedToVerifyAccessToken_thenReturn400() throws Exception {
        MockCookie cookie = new MockCookie(cookieProperties.getName(), "");
        performLogout(cookie).andExpect(status().isUnauthorized());
    }

    @Test
    public void logout_whenAccessCookieNotExists_thenReturn200() throws Exception {
        MockCookie cookie = new MockCookie("another-cookie", "");
        performLogout(cookie).andExpect(status().isOk());
    }


    private MockCookie loginAndGetCookie() throws Exception {
        MvcResult mvcResult = performLogin().andReturn();
        return (MockCookie) mvcResult.getResponse().getCookie(cookieProperties.getName());

    }

    private ResultActions performLogout(Cookie cookie) throws Exception {
        return mockMvc.perform(post(securityProperties.getLogoutPath())
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
    }

    private ResultActions performLogin() throws Exception {
        return mockMvc.perform(post(securityProperties.getLoginPath())
                .param("email", email)
                .param("password", password)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
    }


}

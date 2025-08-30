package app.verimly.task.adapter.security;

import app.verimly.commons.core.adapter.security.TaskAuthenticationServiceAdapter;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AnonymousPrincipal;
import app.verimly.commons.core.security.AuthenticatedPrincipal;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TaskAuthenticationServiceAdapter.class)
class TaskAuthenticationServiceAdapterTest {

    UserId userId;
    Email email;
    SecurityUser authenticatedUser;

    private Authentication authenticated;
    private Authentication unauthenticated;
    private Authentication unknownAuthentication;

    @Autowired
    private TaskAuthenticationServiceAdapter adapter;

    @BeforeEach
    public void setup() {


        userId = UserId.random();
        email = Email.of("muhsener.dev@gmail.com");
        authenticatedUser = new SecurityUser(userId.getValue(), email.getValue(), null);
        authenticated = new UsernamePasswordAuthenticationToken(authenticatedUser, null, new ArrayList<>());
        unauthenticated = new AnonymousAuthenticationToken("Anonymous", new SecurityProperties.User(), List.of(new SimpleGrantedAuthority("ANONYMOUS")));

        unknownAuthentication = new TestingAuthenticationToken(null, null , new ArrayList<>());
    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(adapter);
    }

    @Test
    void getCurrentPrincipal_whenAuthenticated_thenReturnsAuthenticationPrincipalInstance() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticated);
        SecurityContextHolder.setContext(context);

        Principal principal = adapter.getCurrentPrincipal();

        AuthenticatedPrincipal authPrincipal = assertInstanceOf(AuthenticatedPrincipal.class, principal);
        assertEquals(userId, authPrincipal.getId());
        assertEquals(email, authPrincipal.getEmail());
    }

    @Test
    void getCurrentPrincipal_whenAnonymous_thenReturnsAnonymousPrincipal() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(unauthenticated);
        SecurityContextHolder.setContext(context);
        Principal authPrincipal = adapter.getCurrentPrincipal();
        assertInstanceOf(AnonymousPrincipal.class, authPrincipal);
    }

    @Test
    void getCurrentPrincipal_ifAuthenticationIsNull_thenReturnsAnonymousPrincipal() {
        Principal principal = adapter.getCurrentPrincipal();
        assertInstanceOf(AnonymousPrincipal.class, principal);
    }

    @Test
    void getCurrentPrincipal_whenAuthenticationNotRecognized_thenThrowsIllegalStateException() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(unknownAuthentication);
        SecurityContextHolder.setContext(context);

        Executable action = () -> adapter.getCurrentPrincipal();
        assertThrows(IllegalStateException.class,action);

    }

    @AfterEach
    public void clearContext(){
        SecurityContextHolder.clearContext();;
    }
}
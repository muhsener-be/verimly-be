package app.verimly.user.adapter.security.rule;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticatedPrincipal;
import app.verimly.commons.core.security.Principal;
import app.verimly.user.application.ports.out.security.context.ViewUserContext;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ViewUserAuthorizationRule.class)
class ViewUserAuthorizationRuleTest {

    UserId userId;
    Email email;
    Principal principal;
    ViewUserContext context;

    @Autowired
    private ViewUserAuthorizationRule rule;

    @BeforeEach
    void setUp() {
        userId = UserId.random();
        email = Email.of("test@mail.com");
        principal = AuthenticatedPrincipal.of(userId, email);
        context = ViewUserContext.createWithUserId(userId);
    }

    @Test
    void apply_whenPrincipalIsNull_thenThrowsIllegalArgumentException() {
        principal = null;

        assertThrows(IllegalArgumentException.class, getExecutable());


    }

    private @NotNull Executable getExecutable() {
        return () -> rule.apply(principal, context);
    }
}
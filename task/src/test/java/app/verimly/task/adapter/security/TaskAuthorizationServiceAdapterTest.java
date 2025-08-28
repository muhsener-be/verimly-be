package app.verimly.task.adapter.security;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.*;
import app.verimly.task.application.ports.out.security.action.FolderActions;
import app.verimly.task.application.ports.out.security.resource.FolderResource;
import app.verimly.task.domain.vo.folder.FolderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskAuthorizationServiceAdapterTest {

    @Mock
    AuthorizationRuleRegistry registry;

    @InjectMocks
    TaskAuthorizationServiceAdapter adapter;

    Action action;
    Principal principal;
    AuthResource resource;
    AuthorizationRule authorizationRule;

    @BeforeEach
    void setup() {
        action = FolderActions.CREATE;
        principal = new AnonymousPrincipal();
        resource = new FolderResource(FolderId.random(), UserId.random());
        authorizationRule = Mockito.mock(AuthorizationRule.class);
    }


    @Test
    void should_setup_is_ok() {
        assertNotNull(adapter);
    }


    @Test
    void authorize_whenNullPrincipal_thenThrowsIllegalArgumentException() {
        principal = null;

        Executable action = () -> adapter.authorize(principal, this.action, resource);

        assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    void authorize_whenActionIsNull_thenThrowsIllegalArgumentException() {
        action = null;

        Executable executable = () -> adapter.authorize(principal, this.action, resource);

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void authorize_whenArgumentsValid_thenDelegatesAuthorizationRule() {
        when(registry.get(action)).thenReturn(authorizationRule);

        adapter.authorize(principal, action, resource);

        verify(authorizationRule).apply(principal, resource);
    }

    @Test
    void authorize_whenRuleNotFound_thenThrowsIllegalStateException() {

        when(registry.get(action)).thenReturn(null);

        Executable action = () -> adapter.authorize(principal, this.action, null);

        assertThrows(IllegalStateException.class, action);
    }


}
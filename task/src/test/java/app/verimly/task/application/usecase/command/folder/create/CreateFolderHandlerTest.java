package app.verimly.task.application.usecase.command.folder.create;

import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.AuthenticationService;
import app.verimly.commons.core.security.Principal;
import app.verimly.commons.core.security.SecurityException;
import app.verimly.task.application.TaskAbstractUnitTest;
import app.verimly.task.application.event.FolderCreatedApplicationEvent;
import app.verimly.task.application.exception.FolderBusinessException;
import app.verimly.task.application.exception.FolderSystemException;
import app.verimly.task.application.mapper.FolderAppMapper;
import app.verimly.task.application.ports.out.security.TaskAuthorizationService;
import app.verimly.task.application.ports.out.security.context.CreateFolderContext;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.repository.FolderWriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Create Folder Command Handler Test")
public class CreateFolderHandlerTest extends TaskAbstractUnitTest {


    @Mock
    AuthenticationService authN;

    @Mock
    TaskAuthorizationService authZ;

    @Mock
    FolderWriteRepository repository;

    @Mock
    ApplicationEventPublisher eventPublisher;

    @Mock
    FolderAppMapper mapper;

    @InjectMocks
    CreateFolderCommandHandler handler;


    public static final AuthenticationRequiredException AUTHENTICATION_REQUIRED_EXCEPTION = new AuthenticationRequiredException("Test exception.");
    FolderTestData DATA = FolderTestData.getInstance();
    CreateFolderCommand command;
    Principal principal;
    FolderCreationResponse response;


    @BeforeEach
    void setup() {
        command = DATA.createFolderCommand();
        principal = SECURITY_TEST_DATA.authenticatedPrincipal();


    }

    @Test
    @DisplayName("Happy Path")
    void happy_path() {
        when(authN.getCurrentPrincipal()).thenReturn(principal);
        doNothing().when(authZ).authorizeCreateFolder(any(Principal.class), any(CreateFolderContext.class));
        when(repository.save(any(Folder.class))).thenAnswer(returnsFirstArg());
        doNothing().when(eventPublisher).publishEvent(any(FolderCreatedApplicationEvent.class));
        when(mapper.toFolderCreationResponse(any(Folder.class))).thenReturn(response);

        FolderCreationResponse actual = handler.handle(command);

        assertEquals(response, actual);
        verify(authN).getCurrentPrincipal();
        ArgumentCaptor<Principal> principalArgumentCaptor = ArgumentCaptor.forClass(Principal.class);
        verify(authZ).authorizeCreateFolder(principalArgumentCaptor.capture(), any(CreateFolderContext.class));
        assertEquals(principal, principalArgumentCaptor.getValue());
        ArgumentCaptor<Folder> folderCaptor = ArgumentCaptor.forClass(Folder.class);
        verify(repository).save(folderCaptor.capture());
        ArgumentCaptor<FolderCreatedApplicationEvent> eventCaptor = ArgumentCaptor.forClass(FolderCreatedApplicationEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals(eventCaptor.getValue().folder(), folderCaptor.getValue());
        verify(mapper).toFolderCreationResponse(folderCaptor.getValue());

    }

    @Test
    @DisplayName("Failed To Authorize -> SecurityException")
    void whenFailedToAuthorize_thenThrowsSecurityException() {
        when(authN.getCurrentPrincipal()).thenReturn(principal);
        doThrow(AUTHENTICATION_REQUIRED_EXCEPTION).when(authZ).authorizeCreateFolder(any(), any());

        assertThrows(SecurityException.class, () -> handler.handle(command));

        verifyNoInteractions(repository, eventPublisher, repository);
    }

    @Test
    @DisplayName("Invariant Violation -> FolderBusinessException")
    void whenDomainInvariantViolated_thenThrowsFolderBusinessException() {
        command = command.withName(null);
        when(authN.getCurrentPrincipal()).thenReturn(principal);
        doNothing().when(authZ).authorizeCreateFolder(any(Principal.class), any(CreateFolderContext.class));


        assertThrows(FolderBusinessException.class, () -> handler.handle(command));
        verifyNoInteractions(repository, mapper, eventPublisher);
    }


    @Test
    @DisplayName("Failed to Persist -> FolderSystemException")
    void whenFailedToPersist_thenThrowsFolderSystemException() {
        when(authN.getCurrentPrincipal()).thenReturn(principal);
        doNothing().when(authZ).authorizeCreateFolder(any(Principal.class), any(CreateFolderContext.class));
        when(repository.save(any())).thenThrow(TASK_DATA_ACCESS_EXCEPTION);


        assertThrows(FolderSystemException.class, () -> handler.handle(command));
        verifyNoInteractions(mapper);

    }


}

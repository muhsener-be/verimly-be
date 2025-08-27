package app.verimly.task.adapter.web.controller;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.task.adapter.web.dto.request.CreateFolderWebRequest;
import app.verimly.task.adapter.web.dto.response.FolderCreationWebResponse;
import app.verimly.task.adapter.web.mapper.FolderWebMapper;
import app.verimly.task.application.ports.in.FolderApplicationService;
import app.verimly.task.application.usecase.command.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.create.FolderCreationResponse;
import app.verimly.task.data.folder.FolderTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FolderControllerTest {
    @Mock
    FolderApplicationService applicationService;

    @Mock
    FolderWebMapper mapper;

    @InjectMocks
    FolderController controller;

    FolderTestData DATA = FolderTestData.getInstance();
    CreateFolderWebRequest webRequest;
    CreateFolderCommand command;
    FolderCreationResponse response;
    FolderCreationWebResponse webResponse;

    @BeforeEach
    public void setup() {
        webRequest = DATA.createFolderWebRequest();
        command = DATA.createFolderCommand();
        response = DATA.folderCreationResponse();
        webResponse = DATA.folderCreationWebResponse();

        lenient().when(mapper.toCreateFolderCommand(webRequest)).thenReturn(command);
        lenient().when(applicationService.create(command)).thenReturn(response);
        lenient().when(mapper.toFolderCreationWebResponse(response)).thenReturn(webResponse);
    }

    @Test
    void create_givenValidRequest_thenReturnsValidWebResponse() {

        FolderCreationWebResponse actualWebResponse = controller.createFolder(webRequest);

        assertEquals(webResponse, actualWebResponse);
        verify(mapper).toCreateFolderCommand(webRequest);
        verify(applicationService).create(command);
        verify(mapper).toFolderCreationWebResponse(response);
    }

    @Test
    void create_givenProblemDuringMapping_thenThrowsInvalidDomainObjectException() {
        doThrow(InvalidDomainObjectException.class).when(mapper).toCreateFolderCommand(webRequest);


        Executable exec = () -> controller.createFolder(webRequest);


        assertThrows(InvalidDomainObjectException.class, exec);

        verify(mapper).toCreateFolderCommand(webRequest);
        verifyNoInteractions(applicationService);
        verify(mapper,times(0)).toFolderCreationWebResponse(response);
    }
}
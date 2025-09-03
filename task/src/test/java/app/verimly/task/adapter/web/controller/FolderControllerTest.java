package app.verimly.task.adapter.web.controller;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.task.adapter.web.dto.request.CreateFolderWebRequest;
import app.verimly.task.adapter.web.dto.response.FolderCreationWebResponse;
import app.verimly.task.adapter.web.dto.response.FolderSummaryWebResponse;
import app.verimly.task.adapter.web.mapper.FolderWebMapper;
import app.verimly.task.application.dto.FolderSummaryData;
import app.verimly.task.application.ports.in.FolderApplicationService;
import app.verimly.task.application.usecase.command.folder.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.folder.create.FolderCreationResponse;
import app.verimly.task.data.folder.FolderTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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


    @Nested
    @DisplayName("Test of create() method")
    class CreateFolder {

        @Test
        @DisplayName("Happy Path")
        void create_givenValidRequest_thenReturnsValidWebResponse() {

            FolderCreationWebResponse actualWebResponse = controller.createFolder(webRequest);

            assertEquals(webResponse, actualWebResponse);
            verify(mapper).toCreateFolderCommand(webRequest);
            verify(applicationService).create(command);
            verify(mapper).toFolderCreationWebResponse(response);
        }

        @Test
        @DisplayName("Failed to Mapping -> InvalidDomainObjectException")
        void create_givenProblemDuringMapping_thenThrowsInvalidDomainObjectException() {
            doThrow(InvalidDomainObjectException.class).when(mapper).toCreateFolderCommand(webRequest);


            Executable exec = () -> controller.createFolder(webRequest);


            assertThrows(InvalidDomainObjectException.class, exec);

            verify(mapper).toCreateFolderCommand(webRequest);
            verifyNoInteractions(applicationService);
            verify(mapper, times(0)).toFolderCreationWebResponse(response);
        }

        @Test
        @DisplayName("Failed to Mapping -> InvalidDomainObjectException")
        void create_failedInApplicationService_thenThrows() {
            RuntimeException anException = new RuntimeException("Test exception");
            doThrow(anException).when(applicationService).create(command);


            Executable exec = () -> controller.createFolder(webRequest);


            assertThrows(RuntimeException.class, exec);

            verify(mapper).toCreateFolderCommand(webRequest);
            verify(applicationService).create(command);
            verify(mapper, times(0)).toFolderCreationWebResponse(response);
        }


    }


    @Nested
    @DisplayName("Tests of listFolders() method")
    class ListFolders {

        @Test
        void happy_path() {
            List<FolderSummaryWebResponse> mockResponse = Mockito.mock(ArrayList.class);
            List<FolderSummaryData> mockData = Mockito.mock(ArrayList.class);
            when(applicationService.listFolders()).thenReturn(mockData);
            when(mapper.toFolderSummaryWebResponse(mockData)).thenReturn(mockResponse);

            List<FolderSummaryWebResponse> actual = controller.listFolders();

            assertEquals(mockResponse, actual);
            verify(applicationService).listFolders();
            verify(mapper).toFolderSummaryWebResponse(mockData);
        }

        @Test
        void whenProblemInApplicationService_thenThrows() {
            RuntimeException anException = new RuntimeException("An exception");
            doThrow(anException).when(applicationService).listFolders();

            assertThrows(RuntimeException.class, () -> controller.listFolders());

            verifyNoInteractions(mapper);
        }
    }


}
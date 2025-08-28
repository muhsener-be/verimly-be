package app.verimly.task.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.task.adapter.web.dto.request.CreateFolderWebRequest;
import app.verimly.task.adapter.web.dto.response.FolderCreationWebResponse;
import app.verimly.task.application.mapper.TaskVoMapperImpl;
import app.verimly.task.application.usecase.command.folder.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.folder.create.FolderCreationResponse;
import app.verimly.task.data.folder.FolderTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {FolderWebMapperImpl.class, TaskVoMapperImpl.class, CoreVoMapperImpl.class})
class FolderWebMapperTest {


    FolderTestData DATA = FolderTestData.getInstance();
    CreateFolderWebRequest webRequest;
    FolderCreationResponse response;

    @Autowired
    FolderWebMapper mapper;


    @BeforeEach
    void setup() {
        webRequest = DATA.createFolderWebRequest();
        response = DATA.folderCreationResponse();
    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(mapper);
    }

    @Nested
    @DisplayName("toCreateFolderCommand() Tests")
    class CreateFolderCommandTests {
        @Test
        void toCreateFolderCommand_whenNullRequest_thenReturnNull() {
            webRequest = null;

            CreateFolderCommand actual = mapper.toCreateFolderCommand(webRequest);

            assertNull(actual);
        }

        @Test
        void toCreateFolderCommand_whenValidRequest_thenReturnsValidCommand() {
            CreateFolderCommand actual = mapper.toCreateFolderCommand(webRequest);

            assertEquals(webRequest.getName(), actual.name().getValue());
            assertEquals(webRequest.getDescription(), actual.description().getValue());
            assertEquals(webRequest.getLabelColor(), actual.labelColor().getValue());
        }

        @Test
        void toCreateFolderCommand_whenReqeustWithNullFields_thenReturnsCommandWithNullFields() {
            webRequest = DATA.createFolderWebRequestWithNullFields();

            CreateFolderCommand actual = mapper.toCreateFolderCommand(webRequest);

            assertNull(actual.name());
            assertNull(actual.description());
            assertNull(actual.labelColor());
        }
    }


    @Nested
    @DisplayName("toFolderCreationWebResponse() Tests")
    class FolderCreationWebResponseTests {

        @Test
        void whenNullResponse_thenReturnsNull() {
            response = null;

            FolderCreationWebResponse webResponse = mapper.toFolderCreationWebResponse(response);

            assertNull(webResponse);
        }

        @Test
        void whenValidResponse_thenReturnsValidWebResponse() {

            FolderCreationWebResponse webResponse = mapper.toFolderCreationWebResponse(response);

            assertEquals(webResponse.getId(), response.id().getValue());
            assertEquals(webResponse.getOwnerId(), response.ownerId().getValue());
            assertEquals(webResponse.getName(), response.name().getValue());
            assertEquals(webResponse.getDescription(), response.description().getValue());
            assertEquals(webResponse.getLabelColor(), response.labelColor().getValue());
        }

        @Test
        void whenResponseWithNullFields_thenReturnsWebResponseWithNullFields() {
            response = DATA.folderCreationResponseWithNullFields();

            FolderCreationWebResponse webResponse = mapper.toFolderCreationWebResponse(response);

            assertNull(webResponse.getId());
            assertNull(webResponse.getOwnerId());
            assertNull(webResponse.getName());
            assertNull(webResponse.getDescription());
            assertNull(webResponse.getLabelColor());
        }


    }


}
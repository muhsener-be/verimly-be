package app.verimly.task.application.mapper;

import app.verimly.task.application.usecase.command.create.FolderCreationResponse;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.entity.Folder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FolderAppMapperImpl.class)
class FolderAppMapperTest {

    FolderTestData DATA = FolderTestData.getInstance();

    Folder folder;

    @Autowired
    FolderAppMapper mapper;

    @BeforeEach
    void setup() {
        folder = DATA.folder();
    }


    @Test
    void setupIsOk() {
        assertNotNull(mapper);

    }

    @Test
    @DisplayName("toFolderCreationResponse - HAPPY PATH")
    void toFolderCreationResponse_whenValidFolder_thenMapsToResponse() {
        FolderCreationResponse response = mapper.toFolderCreationResponse(folder);

        assertEquals(folder.getId(), response.id());
        assertEquals(folder.getOwnerId(), response.ownerId());
        assertEquals(folder.getName(), response.name());
        assertEquals(folder.getDescription(), response.description());
        assertEquals(folder.getLabelColor(), response.labelColor());
    }

    @Test
    void toFolderCreationResponse_whenNullFolder_thenReturnsNull(){
        folder = null;

        FolderCreationResponse respose = mapper.toFolderCreationResponse(folder);

        assertNull(respose);
    }
}
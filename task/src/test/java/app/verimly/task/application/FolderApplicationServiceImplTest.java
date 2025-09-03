package app.verimly.task.application;

import app.verimly.task.application.usecase.command.folder.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.folder.create.CreateFolderCommandHandler;
import app.verimly.task.application.usecase.command.folder.create.FolderCreationResponse;
import app.verimly.task.application.usecase.query.folder.list.ListFoldersQueryHandler;
import app.verimly.task.data.folder.FolderTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FolderApplicationServiceImplTest extends TaskAbstractUnitTest {

    @Mock
    private CreateFolderCommandHandler createFolderCommandHandler;
    @Mock
    private ListFoldersQueryHandler listFoldersQueryHandler;

    @InjectMocks
    FolderApplicationServiceImpl applicationService;

    CreateFolderCommand command;
    FolderCreationResponse mockResponse;

    @BeforeEach
    void setUp() {
        command = FolderTestData.getInstance().createFolderCommand();
        mockResponse = Mockito.mock(FolderCreationResponse.class);
    }

    @Test
    void create_whenCommandIsNull_thenThrowsIllegalArgumentException() {
        command = null;

        assertThrowsIllegalArgumentException(() -> applicationService.create(command));
    }


}
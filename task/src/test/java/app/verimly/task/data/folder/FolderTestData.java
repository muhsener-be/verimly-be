package app.verimly.task.data.folder;

import app.verimly.commons.core.domain.vo.Color;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.web.dto.request.CreateFolderWebRequest;
import app.verimly.task.adapter.web.dto.response.FolderCreationWebResponse;
import app.verimly.task.application.usecase.command.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.create.FolderCreationResponse;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.vo.FolderDescription;
import app.verimly.task.domain.vo.FolderId;
import app.verimly.task.domain.vo.FolderName;
import com.github.javafaker.Faker;

public class FolderTestData {
    private static final FolderTestData INSTANCE = new FolderTestData();
    private static final Faker FAKER = new Faker();


    public static FolderTestData getInstance() {
        return INSTANCE;
    }


    public UserId ownerId() {
        return UserId.random();
    }

    public FolderId folderId() {
        return FolderId.random();
    }

    public FolderName name() {
        return FolderName.of(FAKER.team().name());
    }


    public FolderDescription description() {
        return FolderDescription.of(FAKER.shakespeare().asYouLikeItQuote());
    }

    public Color labelColor() {
        return Color.of(FAKER.color().hex());
    }

    public Folder folder() {
        return Folder.createWithDescriptionAndLabelColor(ownerId(), name(), description(), labelColor());
    }

    public CreateFolderCommand createFolderCommand() {
        return new CreateFolderCommand(
                name(),
                description(),
                labelColor()
        );
    }


    public CreateFolderWebRequest createFolderWebRequest() {
        return CreateFolderWebRequest.builder()
                .name(name().getValue())
                .description(description().getValue())
                .labelColor(labelColor().getValue())
                .build();
    }

    public CreateFolderWebRequest createFolderWebRequestWithNullFields() {
        return new CreateFolderWebRequest(null, null, null);
    }


    public FolderCreationResponse folderCreationResponse() {
        return new FolderCreationResponse(folderId(), ownerId(), name(), description(), labelColor());
    }

    public FolderCreationResponse folderCreationResponseWithNullFields() {
        return new FolderCreationResponse(null, null, null, null, null);
    }

    public FolderCreationWebResponse folderCreationWebResponse() {
        return new FolderCreationWebResponse(
                folderId().getValue(),
                ownerId().getValue(),
                name().getValue(),
                description().getValue(),
                labelColor().getValue()
        );
    }

    public Folder folderWithNullFields() {
        return Folder.reconstruct(null, null, null, null, null);
    }
}

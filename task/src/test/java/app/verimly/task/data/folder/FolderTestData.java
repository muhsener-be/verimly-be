package app.verimly.task.data.folder;

import app.verimly.commons.core.domain.vo.Color;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.utils.MyStringUtils;
import app.verimly.task.adapter.persistence.entity.FolderEntity;
import app.verimly.task.adapter.web.dto.request.CreateFolderWebRequest;
import app.verimly.task.adapter.web.dto.response.FolderCreationWebResponse;
import app.verimly.task.application.usecase.command.folder.create.CreateFolderCommand;
import app.verimly.task.application.usecase.command.folder.create.FolderCreationResponse;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.vo.folder.FolderDescription;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.folder.FolderName;
import app.verimly.user.adapter.persistence.entity.UserEntity;
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


    private Folder folderInternal(UserId ownerId, FolderName name, FolderDescription description, Color labelColor) {
        return Folder.createWithDescriptionAndLabelColor(ownerId, name, description, labelColor);
    }


    public Folder folderWithNullFields() {
        return Folder.reconstruct(null, null, null, null, null);
    }

    public Folder folderWithOwner(UserId ownerId) {
        return folderInternal(ownerId, name(), description(), labelColor());
    }

    public Folder folderWithFullFields() {
        return folderInternal(ownerId(), name(), description(), labelColor());
    }


    public String nameTooLong() {
        return MyStringUtils.generateString(FolderName.MAX_LENGTH + 1);
    }

    public String descriptionTooLong() {
        return MyStringUtils.generateString(FolderDescription.MAX_LENGTH + 1);
    }

    public FolderEntity folderEntityWithUserId(UserId ownerId) {
        return folderEntityInternal(null, ownerId, null, null, null);

    }

    public FolderEntity folderWithIdAndOwnerId(FolderId folderId, UserId ownerId) {
        return folderEntityInternal(folderId, ownerId, null, null, null);
    }

    public FolderEntity folderEntityInternal(FolderId folderId, UserId ownerId, FolderName name, FolderDescription description, Color labelColor) {
        return FolderEntity.builder()
                .id(folderId == null ? folderId().getValue() : folderId.getValue())
                .description(description == null ? description().getValue() : description.getValue())
                .labelColor(labelColor == null ? labelColor().getValue() : labelColor.getValue())
                .name(name == null ? name().getValue() : name.getValue())
                .ownerId(ownerId == null ? ownerId().getValue() : ownerId.getValue())
                .build();
    }

    public UserEntity userEntityWithId(UserId userId) {
        return UserEntity.builder()
                .id(userId.getValue())
                .email("email@email.com")
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .password(FAKER.random().hex())
                .build();
    }


    public AuthenticationRequiredException authenticationRequiredException() {
        return new AuthenticationRequiredException("Test exception");
    }


}

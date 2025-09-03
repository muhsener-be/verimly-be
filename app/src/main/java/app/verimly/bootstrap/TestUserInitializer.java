package app.verimly.bootstrap;

import app.verimly.commons.core.domain.vo.Color;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.task.domain.vo.folder.FolderName;
import app.verimly.user.adapter.persistence.jparepo.UserJpaRepository;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.CreateUserCommandHandler;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.domain.vo.Password;
import app.verimly.user.domain.vo.PersonName;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Configuration
public class TestUserInitializer {

    private final BootstrapProperties bootstrapProperties;

    public TestUserInitializer(BootstrapProperties bootstrapProperties, UserJpaRepository userJpaRepository, CreateUserCommandHandler createUserCommandHandler, FolderWriteRepository folderWriteRepository) {
        this.bootstrapProperties = bootstrapProperties;
        this.userJpaRepository = userJpaRepository;
        this.createUserCommandHandler = createUserCommandHandler;
        this.folderWriteRepository = folderWriteRepository;

        NAME = PersonName.of(bootstrapProperties.getUser().getFirstName(), bootstrapProperties.getUser().getLastName());
        EMAIL = Email.of(bootstrapProperties.getUser().getEmail());
        PASSWORD = Password.withRaw(bootstrapProperties.getUser().getPassword());
    }

    public final PersonName NAME;
    public final Email EMAIL;
    public final Password PASSWORD;


    public final Color LABEL_COLOR = Color.of("#EAB308");
    public final FolderName FOLDER_NAME = FolderName.of("My Folder");
    public final FolderId FOLDER_ID = FolderId.random();
    private final UserJpaRepository userJpaRepository;
    private final CreateUserCommandHandler createUserCommandHandler;
    private final FolderWriteRepository folderWriteRepository;


    @PostConstruct
    @Transactional
    public void initUser() {
        if (userJpaRepository.count() == 0) {
            CreateUserCommand createUserCommand = new CreateUserCommand(NAME, EMAIL, PASSWORD);
            UserCreationResponse response = createUserCommandHandler.handle(createUserCommand);
            System.out.println("response: " + response);
            Folder folder = Folder.builder()
                    .id(FOLDER_ID)
                    .name(FOLDER_NAME)
                    .labelColor(LABEL_COLOR)
                    .ownerId(response.id())
                    .build();
            Folder savedFolder = folderWriteRepository.save(folder);
            log.info("Test user saved to database: {Email: {}, Password: {}} and default folder: [ ID: {},Name: {} ]", response.email(), PASSWORD, savedFolder.getId(), FOLDER_NAME);
        }

    }
}

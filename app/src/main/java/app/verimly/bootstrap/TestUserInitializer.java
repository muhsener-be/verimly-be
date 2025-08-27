package app.verimly.bootstrap;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.adapter.persistence.jparepo.UserJpaRepository;
import app.verimly.user.application.usecase.command.create.CreateUserCommand;
import app.verimly.user.application.usecase.command.create.CreateUserCommandHandler;
import app.verimly.user.application.usecase.command.create.UserCreationResponse;
import app.verimly.user.domain.vo.Password;
import app.verimly.user.domain.vo.PersonName;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TestUserInitializer {

    public static final UserId USER_ID = UserId.random();
    public static final PersonName NAME = PersonName.of("Muhammet", "Şener");
    public static final Email EMAIL = Email.of("muhsener@mail.com");
    public static final Password PASSWORD = Password.withRaw("muhsener");

    private final UserJpaRepository userJpaRepository;
    private final CreateUserCommandHandler createUserCommandHandler;


    @PostConstruct
    @Transactional
    public void initUser() {
        if (userJpaRepository.count() == 0) {
            CreateUserCommand createUserCommand = new CreateUserCommand(NAME, EMAIL, PASSWORD);
            UserCreationResponse response = createUserCommandHandler.handle(createUserCommand);
            log.info("Test user saved to database: {Email: {}, Password: {}}", EMAIL, "muhsener");
        }

    }
}

package app.verimly.integration.create_task;

import app.verimly.integration.BaseIntegrationTest;
import app.verimly.task.adapter.persistence.entity.FolderEntity;
import app.verimly.task.adapter.persistence.jparepo.FolderJpaRepository;
import app.verimly.task.adapter.web.dto.request.CreateTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.TaskCreationWebResponse;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.repository.FolderWriteRepository;
import app.verimly.task.domain.repository.TaskWriteRepository;
import app.verimly.task.domain.vo.task.TaskId;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.adapter.persistence.jparepo.UserJpaRepository;
import app.verimly.user.application.usecase.command.create.CreateUserCommandHandler;
import app.verimly.utils.FixtureLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTaskIntegrationTest extends BaseIntegrationTest {

    private static final String ENDPOINT = "/api/v1/tasks";
    CreateTaskWebRequest webRequest;
    TaskCreationWebResponse webResponse;

    @Autowired
    TaskWriteRepository taskRepo;

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private FolderJpaRepository folderJpaRepository;


    @BeforeEach
    public void setUp() {
        webRequest = FixtureLoader.loadAndMapTo("fixtures/create-task-web-request.json", CreateTaskWebRequest.class);
        webRequest.setFolderId(bootstrapProps.getFolder().getId());
    }


    @Test
    @DisplayName("Happy Path")
    void whenValidRequest_shouldReturn201() throws Exception {
        MvcResult mvcResult = performCreateTask(loginCookie, 201);

        String contentAsString = mvcResult.getResponse().getContentAsString();
        webResponse = objectMapper.readValue(contentAsString, TaskCreationWebResponse.class);
        TaskId taskId = TaskId.of(webResponse.getId());
        Optional<Task> optional = taskRepo.findById(taskId);
        assertTrue(optional.isPresent());
        taskRepo.deleteTask(optional.get().getId());
    }


    @Test
    void whenUnauthorized_thenReturn401() throws Exception {
        MockCookie mockCookie = new MockCookie("mock_cookie", "broken-value");


        MvcResult mvcResult = performCreateTask(mockCookie, 401);
    }

    @Test
    void whenFolderNotFound_thenReturn403() throws Exception {
        webRequest.setFolderId(UUID.randomUUID());

        MvcResult mvcResult = performCreateTask(loginCookie, 403);

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void whenPrincipalNotOwner_thenReturn403() throws Exception {
        UserEntity userEntity = UserEntity.builder().id(UUID.randomUUID()).firstName("first").lastName("last").email("email@test.com").password("password").build();
        userJpaRepository.save(userEntity);
        FolderEntity folderEntity = FolderEntity.builder().id(UUID.randomUUID()).ownerId(userEntity.getId()).name("folder").build();
        folderJpaRepository.save(folderEntity);

        webRequest.setFolderId(folderEntity.getId());

        performCreateTask(loginCookie, 403);
    }


    private MvcResult performCreateTask(MockCookie cookie, int status) throws Exception {
        return mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(webRequest))
                        .cookie(cookie)
                ).andExpect(status().is(status))
                .andReturn();
    }
}

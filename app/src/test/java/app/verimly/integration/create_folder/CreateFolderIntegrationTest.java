package app.verimly.integration.create_folder;

import app.verimly.commons.core.utils.MyStringUtils;
import app.verimly.integration.BaseIntegrationTest;
import app.verimly.task.adapter.web.dto.request.CreateFolderWebRequest;
import app.verimly.task.domain.vo.folder.FolderDescription;
import app.verimly.task.domain.vo.folder.FolderName;
import app.verimly.utils.FixtureLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CreateFolderIntegrationTest extends BaseIntegrationTest {


    public static final String FIXTURE_PATH = "fixtures/create-folder-web-request.json";
    public static final String ENDPOINT_PATH = "/api/v1/folders";


    CreateFolderWebRequest webRequest;


    @BeforeEach
    void setUp() {
        webRequest = FixtureLoader.loadAndMapTo(FIXTURE_PATH, CreateFolderWebRequest.class);
    }

    @Test
    @DisplayName("Happy Path -> 201")
    void happy_path() throws Exception {
        mockMvc.perform(post(ENDPOINT_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(webRequest))
                .cookie(loginCookie)
        ).andExpect(status().isCreated());
    }


    @Test
    @DisplayName("No Login -> 401")
    void whenNotAuthenticated_thenReturn401() throws Exception {
        mockMvc.perform(post(ENDPOINT_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(webRequest))
        ).andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @DisplayName("Invalid Inputs -> 400")
    @MethodSource("provideInvalidRequests")
    void whenInputInvalid_thenReturn400(CreateFolderWebRequest argRequest) throws Exception {
        String payload = objectMapper.writeValueAsString(argRequest);

        mockMvc.perform(post(ENDPOINT_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(payload)
                .cookie(loginCookie)
        ).andExpect(status().isBadRequest());

    }

    static Stream<CreateFolderWebRequest> provideInvalidRequests() {
        CreateFolderWebRequest request = FixtureLoader.loadAndMapTo(FIXTURE_PATH, CreateFolderWebRequest.class);
        return Stream.of(
                request.toBuilder().name(null).build(),
                request.toBuilder().name(MyStringUtils.generateString(FolderName.MAX_LENGTH + 1)).build(),
                request.toBuilder().description(MyStringUtils.generateString(FolderDescription.MAX_LENGTH + 1)).build(),
                request.toBuilder().labelColor("broken-hex").build()
        );
    }
}

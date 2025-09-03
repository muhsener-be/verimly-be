package app.verimly.integration.create_user;

import app.verimly.bootstrap.BootstrapUserProps;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.integration.BaseIntegrationTest;
import app.verimly.user.adapter.persistence.jparepo.UserJpaRepository;
import app.verimly.user.adapter.web.dto.request.CreateUserWebRequest;
import app.verimly.user.adapter.web.dto.response.UserCreationWebResponse;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.repository.UserWriteRepository;
import app.verimly.utils.FixtureLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class CreateUserIntegrationTest extends BaseIntegrationTest {


    CreateUserWebRequest webRequest;

    @Autowired
    UserWriteRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private BootstrapUserProps userProps;
    @Autowired
    private UserJpaRepository userJpaRepository;


    @BeforeEach
    void setUp() {
        webRequest = FixtureLoader.loadAndMapTo("fixtures/create-user-web-request.json", CreateUserWebRequest.class);
    }

    @Test
    void should_setUp_isOk() {
        assertNotNull(mockMvc);
        assertNotNull(webRequest);
        assertNotNull(objectMapper);
        assertNotNull(userProps);
    }


    @Test
    void whenValidRequest_thenCreatesUserAndSavesToDB() throws Exception {
        // ARRANGE
        ResultMatcher idExist = jsonPath("$.id").exists();

        // ACT
        MvcResult result = performCreateUser(201, idExist);

        // ASSERT
        String responseJson = result.getResponse().getContentAsString();
        UserCreationWebResponse responseDTO = objectMapper.readValue(responseJson, UserCreationWebResponse.class);
        UUID createdUserId = responseDTO.getId();
        Optional<User> optionalUser = repository.findById(UserId.of(createdUserId));


        assertTrue(optionalUser.isPresent());
        User foundUser = optionalUser.get();
        assertEquals(webRequest.getEmail().trim(), foundUser.getEmail().getValue());

        userJpaRepository.deleteById(createdUserId);

    }

    @Test
    void whenFirstNameIsNull_thenReturns400() throws Exception {
        webRequest.setFirstName(null);
        ResultMatcher expect = getErrorCodeExpectationWithLength(1);

        MvcResult result = performCreateUser(400, expect);

    }

    @Test
    void whenFirstNameAndLastNameNull_thenReturn400WithTwoErrorCode() throws Exception {
        webRequest.setFirstName(null);
        webRequest.setLastName(null);

        ResultMatcher expect = getErrorCodeExpectationWithLength(2);

        MvcResult mvcResult = performCreateUser(400, expect);

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void whenNamePartsNullAndEmailNull_thenReturn400WithTwoErrorCode() throws Exception {
        webRequest.setFirstName(null);
        webRequest.setLastName(null);
        webRequest.setEmail(null);


        ResultMatcher expect = getErrorCodeExpectationWithLength(3);

        MvcResult mvcResult = performCreateUser(400, expect);

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void whenNamePartsNullAndEmailInvalid_thenReturn400WithThreeErrorCode() throws Exception {
        webRequest.setFirstName(null);
        webRequest.setLastName(null);
        webRequest.setEmail("");


        ResultMatcher expect = getErrorCodeExpectationWithLength(3);

        MvcResult mvcResult = performCreateUser(400, expect);

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void whenAllFieldsNull_thenReturn400WithFourErrorCode() throws Exception {
        webRequest.setFirstName(null);
        webRequest.setLastName(null);
        webRequest.setEmail(null);
        webRequest.setPassword(null);


        ResultMatcher expect = getErrorCodeExpectationWithLength(4);

        MvcResult mvcResult = performCreateUser(400, expect);

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void whenEmailDuplicated_thenReturn409() throws Exception {
        webRequest.setEmail(userProps.getEmail());

        ResultMatcher expectMatcher = jsonPath("$.resourceType").exists();
        MvcResult mvcResult = performCreateUser(409, expectMatcher);
        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    private ResultMatcher getErrorCodeExpectationWithLength(int length) {
        return jsonPath("$.error_codes.length()").value(Matchers.equalTo(length));
    }


    private MvcResult performCreateUser(int expectedStatus, ResultMatcher andExpect) throws Exception {
        return
                mockMvc.perform(post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(webRequest))
                        )
                        .andExpect(status().is(expectedStatus))
                        .andExpect(andExpect)
                        .andReturn();


    }
}

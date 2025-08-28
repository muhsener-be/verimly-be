package app.verimly.user.adapter.web.controller;

import app.verimly.user.adapter.web.UserExceptionHandler;
import app.verimly.user.adapter.web.dto.request.CreateUserWebRequest;
import app.verimly.user.adapter.web.mapper.UserWebMapper;
import app.verimly.user.application.ports.in.UserApplicationService;
import app.verimly.user.data.UserTestData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({UserController.class, UserExceptionHandler.class})
public class CreateUserValidationTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserWebMapper userWebMapper;

    @MockitoBean
    UserApplicationService userApplicationService;


    UserTestData DATA = UserTestData.getInstance();
    CreateUserWebRequest webRequest;


    @Test
    void setup_is_ok() {
        assertNotNull(mockMvc);
    }

    @BeforeEach
    public void setup() {
        webRequest = DATA.createUserWebRequest();
    }

    @ParameterizedTest
    @MethodSource("supplyInvalidNamePart")
    void invalidFirstName_thenReturns400(String invalidFirstName) throws Exception {
        webRequest.setFirstName(invalidFirstName);
        MvcResult mvcResult = performMockMvc(webRequest, "firstName");

        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println(responseBody);
    }


    @ParameterizedTest
    @MethodSource("supplyInvalidNamePart")
    void nullLastname_thenReturns400(String invalidNamePart) throws Exception {
        webRequest.setLastName(invalidNamePart);
        MvcResult mvcResult = performMockMvc(webRequest, "lastName");

        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println(responseBody);
    }

    static Stream<String> supplyInvalidNamePart() {
        return Stream.of(
                null,
                "",
                "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"
        );
    }

    @ParameterizedTest
    @MethodSource("supplyInvalidEmails")
    void nullEmail_thenReturns400(String invalidEmail) throws Exception {
        webRequest.setEmail(invalidEmail);
        MvcResult mvcResult = performMockMvc(webRequest, "email");

        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println(responseBody);
    }

    static Stream<String> supplyInvalidEmails() {
        return Stream.of(
                null,
                "",
                "     ",
                "plainaddress",
                "@missingusername.com",
                "username@.com",
                "username@com",
                "username@domain..com",
                "username@domain,com",
                "username@domain@domain.com",
                "username@domain .com",
                "username@-domain.com",
                "username@domain.com (Joe Smith)",
                "username@domain..com",
                "username@.domain.com",
                "username@domain.com.",
                "username@.com.com",
                "username@domain..com"
        );
    }


    @ParameterizedTest
    @MethodSource("supplyInvalidPasswords")
    void invalidPassword_thenReturns400(String invalidPassword) throws Exception {
        webRequest.setPassword(invalidPassword);
        MvcResult mvcResult = performMockMvc(webRequest, "password");

        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println(responseBody);
    }

    static Stream<String> supplyInvalidPasswords() {
        return Stream.of(
                null,
                "                s  ",
                "short",
                "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"
        );
    }

    private @NotNull MvcResult performMockMvc(CreateUserWebRequest webRequest, String fieldName) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                        .content(DATA.toJson(webRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.additional.createUserWebRequest.%s".formatted(fieldName)).exists())
                .andReturn();
        return mvcResult;
    }
}

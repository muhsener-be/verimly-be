package app.verimly.task.utils;

import app.verimly.task.adapter.web.dto.request.CreateTaskWebRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;

public class FixtureUtils {

    public static final String CREATE_TASK_WEB_REQUEST_PATH = "fixtures/create-task-web-request.json";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static <T> T loadAndMapTo(String path, Class<T> clazz) {
        try (InputStream is = FixtureUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null)
                throw new RuntimeException("Failed to read input stream from '%s'".formatted(path));


            byte[] bytes = is.readAllBytes();
            return OBJECT_MAPPER.readValue(bytes, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load '%s' due to '%s'".formatted(path, e.getMessage()), e);
        }
    }


    public static CreateTaskWebRequest createTaskWebRequest() {
        return loadAndMapTo(CREATE_TASK_WEB_REQUEST_PATH, CreateTaskWebRequest.class);
    }

}

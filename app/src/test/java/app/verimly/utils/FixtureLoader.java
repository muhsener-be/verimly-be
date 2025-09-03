package app.verimly.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;


public class FixtureLoader {


    private static final com.fasterxml.jackson.databind.ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static <T> T loadAndMapTo(String path, Class<T> clazz) {

        try {
            return OBJECT_MAPPER.readValue(readFile(path), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map '%s' due to '%s'".formatted(path, e.getMessage()), e);
        }
    }

    public static String loadAsString(String path) {
        return readFile(path);
    }

    private static String readFile(String path) {
        try (InputStream is = FixtureLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null)
                throw new RuntimeException("Failed to read input stream from '%s'".formatted(path));


            byte[] bytes = is.readAllBytes();
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load '%s' due to '%s'".formatted(path, e.getMessage()), e);
        }
    }


}

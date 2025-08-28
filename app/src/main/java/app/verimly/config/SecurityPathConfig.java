package app.verimly.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityPathConfig {

    private final SecurityProperties properties;

    @Bean
    public List<String> permitAllPaths() {
        return List.of(
                "/h2-console/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                properties.getSignUpPath(),
                properties.getLoginPath()
        );
    }
}

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
                "/swagger-ui.html/**",
                "/v3/api-docs/**",
                "/favicon.ico",
                properties.getSignUpPath(),
                properties.getLoginPath()
        );
    }
}

package app.verimly.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityProperties properties;
    private final UserDetailsService userDetailsService;
    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, properties.getSignUpPath()).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint((req, res, authException) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        }))
                .formLogin(login ->
                        login.loginProcessingUrl(properties.getLoginPath())
                                .usernameParameter(properties.getUsernameParameter())
                                .passwordParameter(properties.getPasswordParameter())
                                .failureHandler(failureHandler)
                                .successHandler(successHandler))

//                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailsService)
                .build();
    }


    private CorsConfigurationSource configurationSource() {
        CorsProperties props = properties.getCors();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(props.getAllowedOrigins());
        config.setAllowedMethods(props.getAllowedMethods());
        config.setAllowedHeaders(props.getAllowedHeaders());
        config.setAllowCredentials(config.getAllowCredentials());
        config.setMaxAge(props.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(props.getPath(), config);

        return source;

    }
}

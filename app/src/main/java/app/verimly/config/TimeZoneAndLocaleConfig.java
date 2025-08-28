package app.verimly.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.TimeZone;

@Slf4j
@Configuration
public class TimeZoneAndLocaleConfig {

    @Bean
    public SessionLocaleResolver sessionLocaleResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        sessionLocaleResolver.setDefaultTimeZone(TimeZone.getTimeZone("UTC"));
        return sessionLocaleResolver;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        log.debug("Handling request to resolve timezone.");
                        String tzHeader = request.getHeader("X-TimeZone");
                        String langHeader = request.getHeader("Accept-Language");
                        if (langHeader != null) {
                            log.debug("Accept-Language header detected: {} ", langHeader);
                            Locale locale = Locale.of(langHeader);
                            log.debug("Detected header converted to Locale: {} ", locale);

                            LocaleContextHolder.setLocale(locale);
                        } else
                            LocaleContextHolder.setLocale(Locale.US);

                        if (tzHeader != null) {
                            log.debug("TimeZone detected: {}", tzHeader);
                            TimeZone timeZone = TimeZone.getTimeZone(tzHeader);
                            LocaleContextHolder.setTimeZone(timeZone);
                        } else {
                            LocaleContextHolder.setTimeZone(TimeZone.getTimeZone("UTC"));
                        }
                        return true;
                    }
                });
            }
        };
    }
}

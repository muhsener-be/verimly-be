package app.verimly;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@SpringBootConfiguration
public class UserModuleTest {


    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultLocale(Locale.US);
        source.setDefaultEncoding("UTF-8");

        return source;
    }
}

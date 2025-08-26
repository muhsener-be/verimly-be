package app.verimly;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VerimlyApplication {
    public static void main(String[] args) {
        SpringApplication.run(VerimlyApplication.class, args);
    }
}

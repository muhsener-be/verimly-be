package app.verimly.task;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableJpaRepositories
@EnableJpaAuditing
@EntityScan(basePackages = {"app.verimly.task", "app.verimly.user.adapter.persistence.entity"})
public class TaskModuleTests {
}

package app.verimly.task.confg;

import app.verimly.task.domain.factory.TaskFactory;
import app.verimly.task.domain.service.TaskDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskBeanConfig {


    @Bean
    public TaskFactory taskFactory() {
        return new TaskFactory();
    }

    @Bean
    public TaskDomainService taskDomainService(TaskFactory factory) {
        return new TaskDomainService(factory);
    }
}

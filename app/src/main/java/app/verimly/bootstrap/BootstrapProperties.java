package app.verimly.bootstrap;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("bootstrap")
@Component
@Data
public class BootstrapProperties {

    private BootstrapUserProps user;
    private BootstrapFolderProps folder;
}

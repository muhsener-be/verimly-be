package app.verimly.bootstrap;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

@ConfigurationProperties("bootstrap.folder")
@Component
@Data
public class BootstrapFolderProps {
    private UUID id;
    private String name;
    private String description;
    private String labelColor;
}

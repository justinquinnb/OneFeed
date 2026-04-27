package dev.jqb.onefeed.app.config;

import java.nio.file.Paths;
import lombok.Getter;
import lombok.Setter;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onefeed.plugins")
@Getter @Setter
public class PluginConfig {
    private String directory;

    @Bean
    PluginManager pluginManager() {
        if (directory == null || directory.isBlank()) {
            throw new IllegalStateException(
                "Missing required configuration property: onefeed.plugins.directory"
            );
        }
        return new DefaultPluginManager(Paths.get(directory));
    }
}

package dev.jqb.onefeed.config;

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
        return new DefaultPluginManager(Paths.get(directory));
    }
}

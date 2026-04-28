package dev.jqb.onefeed.app.config;

import dev.jqb.onefeed.api.model.data.plugin.PluginEnv;
import dev.jqb.onefeed.api.model.data.plugin.PluginEnvMap;
import dev.jqb.onefeed.app.util.OneFeedPluginManager;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

@Configuration
@ConfigurationProperties("onefeed.plugins")
@Getter @Setter
public class PluginConfig {
    private String directoryPath;
    private String envMapPath;

    private static final Logger logger = LoggerFactory.getLogger(PluginConfig.class);

    @Bean
    public PluginEnvMap pluginEnvMap(Dotenv dotEnv) {
        Path envMapFile = Path.of(envMapPath);

        try {
            logger.debug("Loading plugin-env.yaml at: {}", envMapFile.toAbsolutePath());
            String envMapStr = Files.readString(envMapFile);

            logger.debug("Deserializing plugin-env.yaml...");
            Yaml envYaml = new Yaml();
            PluginEnvMap envMap = envYaml.loadAs(envMapStr, PluginEnvMap.class);

            // Replace all .env variable references
            replaceEnvReferences(envMap, dotEnv);
            return envMap;
        } catch (Exception e) {
            throw new IllegalStateException(
                "Failed to load plugin environment map from file: " + envMapFile.toAbsolutePath(), e
            );
        }
    }

    @Bean
    OneFeedPluginManager oneFeedPluginManager(PluginEnvMap pluginEnvMap) {
        return new OneFeedPluginManager(Path.of(directoryPath), pluginEnvMap);
    }

    /**
     * Replaces all instances of {@code ${VAR_NAME}} in the given {@code envMap}'s values with
     * the corresponding value from OneFeed's environment.
     *
     * @param envMap the map of plugin env variables to replace {@code .env} value references in
     * @param dotEnv the actual environment variables OneFeed is running with from {@code .env}
     */
    private void replaceEnvReferences(PluginEnvMap envMap, Dotenv dotEnv) {
        logger.debug("Retrieving values of .env references in plugin env map...");
        for (String pluginId : envMap.getPlugins().keySet()) {
            PluginEnv pluginEnv = envMap.getPlugins().get(pluginId);

            // Replace arbitrary values
            HashMap<String, String> pluginVars = pluginEnv.getPluginVars();
            replaceEnvReferences(pluginVars, dotEnv);

            // Replace feed values
            HashMap<String, HashMap<String, String>> feedVars = pluginEnv.getFeeds();
            for (String author : feedVars.keySet()) {
                HashMap<String, String> authorVars = feedVars.get(author);
                replaceEnvReferences(authorVars, dotEnv);
            }
        }
    }

    /**
     * Replaces all instances of {@code ${VAR_NAME}} in the given {@code map}'s values with
     * the corresponding value from OneFeed's environment.
     *
     * @param map the map whose {@code .env} value references to replace
     * @param dotEnv the actual environment variables OneFeed is running with from {@code .env}
     */
    private void replaceEnvReferences(HashMap<String, String> map, Dotenv dotEnv) {
        for (String key : map.keySet()) {
            String value = map.get(key);
            boolean isEnvRef = value.startsWith("${");
            isEnvRef = isEnvRef && value.endsWith("}");
            if (isEnvRef) {
                String envKey = map.get(key).substring(2, map.get(key).length() - 1);

                if (envKey.isEmpty()) {
                    throw new IllegalArgumentException("plugin-env.yaml .env reference cannot be empty");
                }

                String envVal = dotEnv.get(envKey);
                Objects.requireNonNull(envVal, "Environment variable " + envKey +
                    " is referenced in plugin-env.yaml, but is null");

                logger.trace("Replaced reference to {} in .env file with actual value", key);
                map.put(key, envVal);
            }
        }
    }
}

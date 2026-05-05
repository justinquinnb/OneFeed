package dev.jqb.onefeed.app.config;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import dev.jqb.onefeed.api.plugin.ProviderEnv;
import dev.jqb.onefeed.api.plugin.ProviderEnvsFile;
import dev.jqb.onefeed.app.util.OneFeedPluginManager;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;
import tools.jackson.databind.ObjectMapper;

@Configuration
@ConfigurationProperties("onefeed.plugins")
@Getter
@Setter
public class PluginConfig {
    private String directoryPath;
    private String providerEnvsPath;

    private static final Logger logger = LoggerFactory.getLogger(PluginConfig.class);

    @Bean
    public ProviderEnvsFile providerEnvsFile(Dotenv dotEnv) {
        Path envMapFile = Path.of(providerEnvsPath);

        try {
            logger.debug("Loading provider envs from: {}", envMapFile.toAbsolutePath());
            String envMapStr = Files.readString(envMapFile);

            logger.debug("Deserializing {}...", envMapFile.toAbsolutePath());
            YAMLMapper mapper = YAMLMapper.builder().build();
            ProviderEnvsFile envsFile = mapper.readValue(envMapStr, ProviderEnvsFile.class);

            // Replace all .env variable references
            replaceEnvReferences(envsFile, dotEnv);
            return envsFile;
        } catch (Exception e) {
            throw new IllegalStateException(
                "Failed to load plugin environment map from file: " + envMapFile.toAbsolutePath(), e
            );
        }
    }

    @Bean
    OneFeedPluginManager oneFeedPluginManager(ProviderEnvsFile providerEnvsFile) {
        return new OneFeedPluginManager(Path.of(directoryPath), providerEnvsFile);
    }

    /**
     * Replaces all instances of {@code ${VAR_NAME}} in the given {@code envMap}'s values with
     * the corresponding value from OneFeed's environment.
     *
     * @param providerEnvsFile the map of plugin env variables to replace {@code .env} value
     *                       references in
     * @param dotEnv the actual environment variables OneFeed is running with from {@code .env}
     */
    private void replaceEnvReferences(ProviderEnvsFile providerEnvsFile, Dotenv dotEnv) {
        logger.debug("Retrieving values of .env references in plugin env map...");
        for (String pluginId : providerEnvsFile.getProviderEnvs().keySet()) {
            ProviderEnv pluginEnv = providerEnvsFile.getProviderEnvs().get(pluginId);

            // Replace arbitrary values
            HashMap<String, String> pluginVars = pluginEnv.getPluginVars();
            replaceEnvReferences(pluginVars, dotEnv);

            // Replace feed values
            HashMap<String, HashMap<String, String>> feedsMap = pluginEnv.getFeeds();
            for (String feedName : feedsMap.keySet()) {
                HashMap<String, String> feedVars = feedsMap.get(feedName);
                replaceEnvReferences(feedVars, dotEnv);
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
                    throw new IllegalArgumentException("provider-envs.yaml .env reference cannot be empty");
                }

                String envVal = dotEnv.get(envKey);
                Objects.requireNonNull(envVal, "Environment variable " + envKey +
                    " is referenced in provider-envs.yaml, but is null");

                logger.trace("Replaced reference to {} in .env file with actual value", key);
                map.put(key, envVal);
            }
        }
    }
}

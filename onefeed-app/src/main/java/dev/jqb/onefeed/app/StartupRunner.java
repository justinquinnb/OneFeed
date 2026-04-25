package dev.jqb.onefeed.app;

import java.util.stream.Collectors;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Runs on application startup, with the Spring context already initialized
 */
@Component
public class StartupRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);
    private PluginManager pluginManager;

    @Autowired
    public StartupRunner(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initPlugins();
    }

    /**
     * Initialize plugins from the configured plugin directory
     */
    private void initPlugins() {
        logger.info("Loading plugins from {}", pluginManager.getPluginsRoots());
        pluginManager.loadPlugins();
        String loadedPluginsList = pluginManager.getPlugins().stream().map(plugin ->
            "\n\t" + plugin.getClass().getSimpleName() +
                " (v" + plugin.getDescriptor().getVersion() + ") by "
                + plugin.getDescriptor().getProvider()
        ).collect(Collectors.joining());

        if (pluginManager.getPlugins().size() == 0) {
            return;
        }

        logger.info("{} plugins loaded: {}", pluginManager.getPlugins().size(), loadedPluginsList);

        logger.info("Starting plugins...");
        pluginManager.startPlugins();
    }
}

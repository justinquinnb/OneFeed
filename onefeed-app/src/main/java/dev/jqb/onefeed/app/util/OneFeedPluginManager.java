package dev.jqb.onefeed.app.util;

import dev.jqb.onefeed.api.plugin.PluginEnvMap;
import java.nio.file.Path;
import java.util.Objects;
import org.pf4j.DefaultPluginManager;

/**
 * OneFeed's custom plugin manager
 */
public class OneFeedPluginManager extends DefaultPluginManager {

    public OneFeedPluginManager(Path pluginsPath, PluginEnvMap pluginEnvMap) {
        super(pluginsPath);
        Objects.requireNonNull(pluginEnvMap, "pluginEnvMap must not be null");
        this.pluginFactory = new OneFeedPluginFactory(pluginEnvMap);
    }
}

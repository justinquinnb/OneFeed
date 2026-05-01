package dev.jqb.onefeed.app.util;

import dev.jqb.onefeed.api.plugin.PluginEnv;
import dev.jqb.onefeed.api.plugin.PluginEnvMap;
import java.lang.reflect.Constructor;
import org.pf4j.DefaultPluginFactory;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OneFeed's custom plugin factory, providing plugin-specific env variables to the plugin classes
 * themselves
 */
public class OneFeedPluginFactory extends DefaultPluginFactory {
    private static final Logger logger = LoggerFactory.getLogger(OneFeedPluginFactory.class);

    /**
     * The complete set of environment variables OneFeed has been started with,
     */
    private PluginEnvMap envMap;

    public OneFeedPluginFactory(PluginEnvMap envMap) {
        super();
        this.envMap = envMap;
    }

    @Override
    protected Plugin createInstance(Class<?> pluginClass, PluginWrapper pluginWrapper) {
        PluginEnv pluginEnv = envMap.getPlugins().get(pluginWrapper.getPluginId());
        try {
            Constructor<?> constructor = pluginClass.getConstructor(PluginEnv.class);
            return (Plugin) constructor.newInstance(pluginEnv);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}

package dev.jqb.onefeed.app.plugins;

import dev.jqb.onefeed.api.plugin.OneFeedProviderPlugin;
import dev.jqb.onefeed.api.plugin.ProviderEnv;
import dev.jqb.onefeed.api.plugin.ProviderEnvsFile;
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
    private final ProviderEnvsFile providerEnvsFile;

    public OneFeedPluginFactory(ProviderEnvsFile providerEnvsFile) {
        super();
        this.providerEnvsFile = providerEnvsFile;
    }

    @Override
    protected Plugin createInstance(Class<?> pluginClass, PluginWrapper pluginWrapper) {
        try {
            // Pass the provider env map to the class if it's a plugin provider
            if (OneFeedProviderPlugin.class.isAssignableFrom(pluginClass)) {
                ProviderEnv pluginEnv = providerEnvsFile.getProviderEnvs()
                    .get(pluginWrapper.getPluginId());

                Constructor<?> constructor = pluginClass.getConstructor(ProviderEnv.class);
                return (Plugin) constructor.newInstance(pluginEnv);
            }

            Constructor<?> constructor = pluginClass.getConstructor();
            return (Plugin) constructor.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}

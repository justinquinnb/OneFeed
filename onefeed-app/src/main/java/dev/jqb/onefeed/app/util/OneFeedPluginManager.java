package dev.jqb.onefeed.app.util;

import dev.jqb.onefeed.api.plugin.OneFeedProviderPlugin;
import dev.jqb.onefeed.api.plugin.ProviderEnvsFile;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;

/**
 * OneFeed's custom plugin manager
 */
public class OneFeedPluginManager extends DefaultPluginManager {

    public OneFeedPluginManager(Path pluginsPath, ProviderEnvsFile providerEnvsFile) {
        super(pluginsPath);
        Objects.requireNonNull(providerEnvsFile, "providerEnvsFile arg must not be null");
        this.pluginFactory = new OneFeedPluginFactory(providerEnvsFile);
    }

    /**
     * Gets a list of all {@link PluginState#RESOLVED} {@link OneFeedProviderPlugin}s.
     * @return a list of all {@link PluginState#RESOLVED} {@link OneFeedProviderPlugin}s
     */
    public List<PluginWrapper> getProviders() {
        return getPlugins().stream()
            .filter(p -> p.getPlugin() instanceof OneFeedProviderPlugin).toList();
    }
}

package dev.jqb.onefeed.api.plugin;

import dev.jqb.onefeed.api.pipeline.Provider;
import lombok.Getter;
import lombok.Setter;
import org.pf4j.Plugin;

/**
 * A plugin for OneFeed, representing the package containing a {@link Provider} implementation
 */
@Getter
@Setter
public abstract class OneFeedPlugin extends Plugin {

    /**
     * The environment variables specific to this plugin, as specified in OneFeed's
     * {@code plugin-env.yaml} and {@code .env} files.
     */
    protected PluginEnv pluginEnv;

    /**
     * Constructs a new {@link OneFeedPlugin} with the given {@code pluginEnv}.
     * @param pluginEnv the environment variables specific to this plugin
     *
     * @see #pluginEnv
     */
    protected OneFeedPlugin(PluginEnv pluginEnv) {
        super();
        this.pluginEnv = pluginEnv;
    }
}

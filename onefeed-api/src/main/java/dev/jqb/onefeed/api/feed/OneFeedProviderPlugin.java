package dev.jqb.onefeed.api.feed;

import dev.jqb.onefeed.api.content.RawContent;
import dev.jqb.onefeed.api.plugin.OneFeedPlugin;

/**
 * A OneFeed {@link Provider} plugin
 */
public abstract class OneFeedProviderPlugin extends OneFeedPlugin {
    /**
     * The environment variables specific to this plugin, as specified in OneFeed's
     * {@code plugin-env.yaml} and {@code .env} files.
     */
    protected ProviderConfig providerConfig;

    /**
     * Constructs a new {@link OneFeedPlugin} with the given {@code providerEnv}.
     * @param providerConfig the environment variables specific to this plugin
     *
     * @see #providerConfig
     */
    protected OneFeedProviderPlugin(ProviderConfig providerConfig) {
        super();
        this.providerConfig = providerConfig;
    }

    /**
     * Gets the content {@link Provider} that this plugin... well, provides.
     * @return the content {@link Provider} that this plugin provides
     */
    public abstract Provider<? extends RawContent> getProvider();
}

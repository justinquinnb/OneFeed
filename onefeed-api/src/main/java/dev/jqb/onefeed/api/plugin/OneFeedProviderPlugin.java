package dev.jqb.onefeed.api.plugin;

import dev.jqb.onefeed.api.content.RawContent;
import dev.jqb.onefeed.api.pipeline.Provider;

/**
 * A OneFeed {@link Provider} plugin
 */
public abstract class OneFeedProviderPlugin extends OneFeedPlugin {
    /**
     * The environment variables specific to this plugin, as specified in OneFeed's
     * {@code plugin-env.yaml} and {@code .env} files.
     */
    protected ProviderEnv providerEnv;

    /**
     * Constructs a new {@link OneFeedPlugin} with the given {@code providerEnv}.
     * @param providerEnv the environment variables specific to this plugin
     *
     * @see #providerEnv
     */
    protected OneFeedProviderPlugin(ProviderEnv providerEnv) {
        super();
        this.providerEnv = providerEnv;
    }

    /**
     * Gets the content {@link Provider} that this plugin... well, provides.
     * @return the content {@link Provider} that this plugin provides
     */
    public abstract Provider<? extends RawContent> getProvider();
}

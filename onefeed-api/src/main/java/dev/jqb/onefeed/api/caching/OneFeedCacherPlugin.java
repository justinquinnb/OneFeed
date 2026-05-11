package dev.jqb.onefeed.api.caching;

import dev.jqb.onefeed.api.plugin.OneFeedPlugin;

public abstract class OneFeedCacherPlugin extends OneFeedPlugin {
    /**
     * The environment variables specific to this plugin, as specified in OneFeed's
     * {@code plugin-env.yaml} and {@code .env} files.
     */
    protected CacherEnv cacherEnv;

    /**
     * Constructs a new {@link OneFeedPlugin} with the given {@code cacherEnv}.
     * @param cacherEnv the environment variables specific to this plugin
     *
     * @see #cacherEnv
     */
    protected OneFeedCacherPlugin(CacherEnv cacherEnv) {
        super();
        this.cacherEnv = cacherEnv;
    }

    /**
     * Gets the {@link Cacher} service that this plugin provides.
     * @return the {@link Cacher} service that this plugin provides
     */
    public abstract Cacher getCacher();
}

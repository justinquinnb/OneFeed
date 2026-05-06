package dev.jqb.onefeed.api.plugin;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * A provider plugin's specific environment variables, including those specific to any of its feeds
 */
@Getter
@Setter
public class ProviderEnv {

    /**
     * Plugin-specific configuration of arbitrary shape
     */
    private HashMap<String, Object> pluginVars;

    /**
     * A mapping of arbitrary feed names to arbitrarily shaped, feed-specific configuration data
     */
    private HashMap<String, HashMap<String, Object>> feeds;

    public ProviderEnv() {}

    /**
     * Creates a new {@link ProviderEnv} object with the given {@code pluginVars} and {@code feedVars}
     *
     * @param pluginVars plugin-specific configuration of arbitrary shape
     * @param feeds a mapping of arbitrary feed names to arbitrarily shaped, feed-specific
     *              configuration data
     */
    public ProviderEnv(HashMap<String, Object> pluginVars,
        HashMap<String, HashMap<String, Object>> feeds
    ) {
        this.pluginVars = pluginVars;
        this.feeds = feeds;
    }
}

package dev.jqb.onefeed.api.model.data.plugin;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * A plugin's specific environment variables, including those specific to any of its feeds
 */
@Getter
@Setter
public class PluginEnv {

    /**
     * Arbitrarily keyed plugin-specific environment variables. Represents plugin-wide data.
     */
    private HashMap<String, String> pluginVars;

    /**
     * A mapping of feed author names to arbitrary author/feed-specific KVPs
     */
    private HashMap<String, HashMap<String, String>> feeds;

    public PluginEnv() {}

    /**
     * Creates a new {@link PluginEnv} object with the given {@code pluginVars} and {@code feedVars}
     *
     * @param pluginVars arbitrarily keyed plugin-specific environment variables, representing
     *                   plugin-wide data
     * @param feeds a mapping of feed author names to arbitrary author/feed-specific KVPs
     */
    public PluginEnv(HashMap<String, String> pluginVars,
        HashMap<String, HashMap<String, String>> feeds
    ) {
        this.pluginVars = pluginVars;
        this.feeds = feeds;
    }
}

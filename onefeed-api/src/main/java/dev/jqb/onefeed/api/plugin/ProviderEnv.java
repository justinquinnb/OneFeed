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
     * Arbitrarily keyed plugin-specific environment variables. Represents plugin-wide data.
     */
    private HashMap<String, String> pluginVars;

    /**
     * A mapping of feed names to arbitrary feed-specific KVPs
     */
    private HashMap<String, HashMap<String, String>> feeds;

    public ProviderEnv() {}

    /**
     * Creates a new {@link ProviderEnv} object with the given {@code pluginVars} and {@code feedVars}
     *
     * @param pluginVars arbitrarily keyed plugin-specific environment variables, representing
     *                   plugin-wide data
     * @param feeds a mapping of feed names to arbitrary feed-specific KVPs
     */
    public ProviderEnv(HashMap<String, String> pluginVars,
        HashMap<String, HashMap<String, String>> feeds
    ) {
        this.pluginVars = pluginVars;
        this.feeds = feeds;
    }
}

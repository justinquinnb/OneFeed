package dev.jqb.onefeed.api.model.data.plugin;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * A type corresponding to the mostly-known shape of the plugin environment map, used for its
 * parsing
 */
@Getter
@Setter
public class PluginEnvMap {

    /**
     * The map data, a mapping of plugin IDs to their specific "environment" variables
     */
    private HashMap<String, PluginEnv> plugins;

    public PluginEnvMap() {}

    /**
     * Creates a new {@link PluginEnvMap} object with the given {@code data}
     * @param pluginEnvs the data from the environment map YAML file
     */
    public PluginEnvMap(HashMap<String, PluginEnv> pluginEnvs) {
        this.plugins = pluginEnvs;
    }
}

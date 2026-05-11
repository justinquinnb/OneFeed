package dev.jqb.onefeed.api.plugin;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jqb.onefeed.api.caching.CacherEnv;
import dev.jqb.onefeed.api.feed.ProviderEnv;
import java.util.HashMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A type corresponding to the mostly known shape of the provider environment map file, used for its
 * parsing. Allows plugins to reference environment variables for arbitrarily named feeds
 * with static keys and no concern for naming conflicts.
 */
@Getter
@Setter
@NoArgsConstructor
public class PluginEnvsFile {

    /**
     * A mapping of provider plugin IDs to their specific "environment" variables
     */
    @JsonProperty("provider-envs")
    private HashMap<String, ProviderEnv> providerEnvs;

    /**
     * A mapping of cacher plugin IDs to their specific "environment" variables
     */
    @JsonProperty("cacher-envs")
    private HashMap<String, CacherEnv> cacherEnvs;

    /**
     * Creates a new {@link PluginEnvsFile} object with the given {@code data}
     * @param providerEnvs the data from the environment map YAML file
     */
    public PluginEnvsFile(HashMap<String, ProviderEnv> providerEnvs,
        HashMap<String, CacherEnv> cacherEnvs
    ) {
        this.providerEnvs = providerEnvs;
        this.cacherEnvs = cacherEnvs;
    }
}

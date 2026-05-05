package dev.jqb.onefeed.api.plugin;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * A type corresponding to the mostly known shape of the provider environment map file, used for its
 * parsing. Allows provider plugins to reference environment variables for arbitrarily named feeds
 * with static keys and no concern for naming conflicts.
 */
@Getter
@Setter
public class ProviderEnvsFile {

    /**
     * A mapping of provider plugin IDs to their specific "environment" variables
     */
    @JsonProperty("provider-envs")
    private HashMap<String, ProviderEnv> providerEnvs;

    public ProviderEnvsFile() {}

    /**
     * Creates a new {@link ProviderEnvsFile} object with the given {@code data}
     * @param providerEnvs the data from the environment map YAML file
     */
    public ProviderEnvsFile(HashMap<String, ProviderEnv> providerEnvs) {
        this.providerEnvs = providerEnvs;
    }
}

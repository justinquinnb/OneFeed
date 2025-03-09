package com.justinquinnb.onefeed.customization.config;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * OneFeed's meta configuration specs, such as API rate limit and authorization.
 */
@JacksonXmlRootElement(localName = "meta")
public class MetaConfig {
    /**
     * OneFeed's API key.
     */
    @JacksonXmlProperty(localName = "api-key")
    private String apiKey;

    /**
     * Instantiates some {@link MetaConfig} with the properties specified below.
     *
     * @param apiKey OneFeed's API key
     */
    public MetaConfig(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Updates OneFeed's API key to that provided in {@code apiKey}.
     *
     * @param apiKey the new OneFeed API key to enforce
     */
    public void setApiKey(String apiKey) {
        if (apiKey.startsWith("${") && apiKey.endsWith("}")) {
            this.apiKey = System.getenv(apiKey.substring(2, apiKey.length() - 1));
        }
    }

    /**
     * Retrieves the API key currently enforced by OneFeed.
     *
     * @return the API key currently enforced by OneFeed
     */
    public String getApiKey() {
        return this.apiKey;
    }
}

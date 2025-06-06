package com.justinquinnb.onefeed.api;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

/**
 * Holds OneFeed API config info.
 */
@Configuration
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix="onefeed.api")
public class OneFeedApiConfig {
    /**
     * ENV-provided API key to access OneFeed's endpoints.
     */
    @NotNull
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
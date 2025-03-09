package com.justinquinnb.onefeed.customization.config;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * OneFeed's global configuration.
 */
@JacksonXmlRootElement(localName = "config")
public class OneFeedCompleteConfig {
    /**
     * OneFeed's meta properties, such as rate limit and authorization.
     */
    @JacksonXmlProperty(localName = "meta")
    @JacksonXmlElementWrapper(useWrapping = false)
    private MetaConfig metaConfig;

    /**
     * Instantiates some {@link OneFeedCompleteConfig} with the meta configurations specified by {@code metaConfig}/
     *
     * @param metaConfig an instance of {@code MetaConfig} properties to house in the constructed
     * {@code OneFeedCompleteConfig} instance
     */
    public OneFeedCompleteConfig(
            @JacksonXmlProperty(localName = "meta") MetaConfig metaConfig) {
        this.metaConfig = metaConfig;
    }

    /**
     *
     * @return
     */
    public MetaConfig getMetaConfig() {
        return this.metaConfig;
    }

    /**
     *
     * @param metaConfig
     */
    public void setMetaConfig(MetaConfig metaConfig) {

    }
}
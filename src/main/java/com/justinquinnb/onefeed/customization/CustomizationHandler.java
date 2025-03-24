package com.justinquinnb.onefeed.customization;

import com.justinquinnb.onefeed.OneFeedApplication;
import com.justinquinnb.onefeed.content.details.ContentSourceId;
import com.justinquinnb.onefeed.customization.config.ConfigReader;
import com.justinquinnb.onefeed.customization.config.OneFeedCompleteConfig;
import com.justinquinnb.onefeed.customization.source.ContentSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * Loads and manages OneFeed's configurations, modifications, and addons.
 */
@Component
public class CustomizationHandler {
    /**
     * The default filepath to OneFeed's config XML.
     */
    private static final String DEFAULT_CONFIG_FILEPATH = "onefeed-config.xml";

    /**
     * A mapping of the currently-enabled {@link ContentSource}s to their {@link ContentSourceId} literals,
     * {@link ContentSourceId#getId()}.
     */
    private static HashMap<String, ContentSource<?>> ENABLED_CONTENT_SOURCES = new HashMap<>();

    /**
     * A mapping of the currently-disabled {@link ContentSource}s to their {@link ContentSourceId} literals,
     * {@link ContentSourceId#getId()}.
     */
    private static HashMap<String, ContentSource<?>> DISABLED_CONTENT_SOURCES = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(CustomizationHandler.class);

    /**
     * Loads all of OneFeed's default and user-added customizations (addons and config).
     */
    public static void loadCustomizations() throws IOException {
        logger.info("Loading customizations...");

        // LOAD CONFIGURATIONS
        // OneFeed global config
        OneFeedCompleteConfig oneFeedCompleteConfig = ConfigReader.getConfig(DEFAULT_CONFIG_FILEPATH);
        OneFeedApplication.provideOneFeedCompleteConfig(oneFeedCompleteConfig);

        // LOAD ADDONS
        // Content Sources


        logger.info("Customizations successfully loaded.");
    }

    /**
     * Checks whether the provided {@link ContentSourceId} is already in use by another {@link ContentSource}.
     *
     * @param contentSourceId the {@code ContentSourceId} whose availability to check
     *
     * @return {@code true} if no other currently-instantiated {@link ContentSource}s are employing the same
     * {@code ContentSourceId}, {@code false} otherwise
     */
    public static boolean isCsIdAvailable(ContentSourceId contentSourceId) {
        return !(ENABLED_CONTENT_SOURCES.containsKey(contentSourceId.toString()) ||
                DISABLED_CONTENT_SOURCES.containsKey(contentSourceId.toString()));
    }
}
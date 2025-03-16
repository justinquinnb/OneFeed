package com.justinquinnb.onefeed.customization;

import com.justinquinnb.onefeed.OneFeedApplication;
import com.justinquinnb.onefeed.customization.config.ConfigReader;
import com.justinquinnb.onefeed.customization.config.OneFeedCompleteConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Loads and manages OneFeed's configurations, modifications, and addons.
 */
@Component
public class CustomizationHandler {
    /**
     * The default filepath to OneFeed's config XML.
     */
    private static final String DEFAULT_CONFIG_FILEPATH = "onefeed-config.xml";

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
}
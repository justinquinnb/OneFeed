package com.justinquinnb.onefeed.customization.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ConfigReader {
    /**
     * XML mapper to instantiate {@link OneFeedCompleteConfig} from serialized data.
     */
    private static final ObjectMapper XML_MAPPER = new XmlMapper();

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    /**
     * Deserializes the XML at the provided {@code xmlFilepath} into an instance of {@link OneFeedCompleteConfig}.
     *
     * @param filepath the path to the XML file containing the serialized {@code OneFeedCompleteConfig} object
     *
     * @return a {@code OneFeedCompleteConfig} instance matching the data serialized in the XML at {@code filepath}
     * @throws IOException if parsing could not be completed
     */
    public static OneFeedCompleteConfig getConfig(String filepath) throws IOException {
        logger.info("Attempting to parse OneFeed's configurations...");
        File configFile = new File(filepath);
        OneFeedCompleteConfig oneFeedCompleteConfig = XML_MAPPER.readValue(configFile, OneFeedCompleteConfig.class);
        logger.info("OneFeed's configurations successfully parsed.");
        return oneFeedCompleteConfig;
    }
}
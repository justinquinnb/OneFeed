package com.justinquinnb.onefeed.contentsources.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.justinquinnb.onefeed.data.model.content.BasicContent;
import com.justinquinnb.onefeed.data.model.content.details.BasicPlatform;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Provides an offline, tinker-able source of content to pull from, emulating a full-fledged Content Source.
 */
public class SampleService extends ContentSource {
    private static final Platform INFO = new BasicPlatform("N/A", "Sample Service", "@");
    private static final String SAMPLE_CONTENT_LOCATION = "src/main/java/com/justinquinnb/onefeed/contentsources/sample/sample-content.json";

    private static final String baseUrl = "sampleurl";
    private static final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    public SampleService(String id) {
        super(id);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public BasicContent[] getLatestContent(int count) {
        int numToGet = Math.min(count, 6);
        BasicContent[] sampleContent = new BasicContent[0];
        try {
            sampleContent = mapper.readValue(
                    new File(SAMPLE_CONTENT_LOCATION),
                    BasicContent[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sampleContent;
    }

    @Override
    public BasicContent[] getLatestContent(int count, Instant[] betweenTimes) {
        int numToGet = Math.min(count, 6);
        BasicContent[] sampleContent = new BasicContent[0];
        try {
            sampleContent = mapper.readValue(
                    new File(SAMPLE_CONTENT_LOCATION),
                    BasicContent[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Narrow the sample content down to only those within the date range
        Collection<BasicContent> filteredContent = new ArrayList<>();
        for (BasicContent content : sampleContent) {
            Instant contentTimestamp = content.getTimestamp();
            if (isInclusiveBetween(contentTimestamp, betweenTimes[0], betweenTimes[1])) {
                filteredContent.add(content);
            }
        }

        return filteredContent.toArray(new BasicContent[0]);
    }

    @Override
    public Platform getSourceInfo() {
        return INFO;
    }
}
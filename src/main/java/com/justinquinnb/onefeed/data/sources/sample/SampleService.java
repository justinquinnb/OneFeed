package com.justinquinnb.onefeed.data.sources.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.source.APIEndpoint;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public class SampleService extends APIEndpoint {
    private static final String baseUrl = "sampleurl";
    private static final String sourceName = "Sample";
    private static final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @Override
    protected String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public Content[] getLatestContent(int count) {
        int numToGet = (count > 10) ? 10 : count;
        Content[] sampleContent = new Content[0];
        try {
            sampleContent = mapper.readValue(
                    new File("src/main/java/com/justinquinnb/onefeed/data/sources/sample/sample-content.json"),
                    SampleContent[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sampleContent;
    }

    @Override
    public Content[] getLatestContent(int count, Instant[] betweenTimes) {
        int numToGet = (count > 10) ? 10 : count;
        Content[] sampleContent = new Content[0];
        try {
            sampleContent = mapper.readValue(
                    new File("src/main/java/com/justinquinnb/onefeed/data/sources/sample/sample-content.json"),
                    SampleContent[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Narrow the sample content down to only those within the date range
        Collection<Content> filteredContent = new ArrayList<>();
        for (Content content : sampleContent) {
            Instant contentTimestamp = content.getTimestamp();
            if (isInclusiveBetween(contentTimestamp, betweenTimes[0], betweenTimes[1])) {
                filteredContent.add(content);
            }
        }

        return filteredContent.toArray(new Content[0]);
    }

    @Override
    public String getSourceName() {
        return sourceName;
    }
}
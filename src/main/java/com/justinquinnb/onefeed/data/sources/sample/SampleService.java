package com.justinquinnb.onefeed.data.sources.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.content.details.Producer;
import com.justinquinnb.onefeed.data.model.source.APIEndpoint;
import com.justinquinnb.onefeed.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class SampleService extends APIEndpoint {
    private static final String baseUrl = "sampleurl";
    private static final String sourceName = "Sample";

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
        int numToGet = (!(count <= 0) || count > 10) ? 10 : count;
        Content[] content = new Content[count];

        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        Content[] sampleContent = new Content[10];
        try {
            sampleContent = mapper.readValue(
                    new File("src/main/java/com/justinquinnb/onefeed/data/sources/sample/sample-content.json"),
                    SampleContent[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.arraycopy(sampleContent, 0, content, 0, count);

        return content;
    }

    @Override
    public String getSourceName() {
        return sourceName;
    }
}
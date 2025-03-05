package com.justinquinnb.onefeed.contentsources.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.justinquinnb.onefeed.data.model.content.BasicContent;
import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.BasicPlatform;
import com.justinquinnb.onefeed.data.model.content.details.ContentSourceId;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Provides an offline, tinker-able source of content to pull from, emulating a full-fledged {@link ContentSource}.
 */
public class SampleService extends ContentSource {
    /**
     * Constant information about the "{@link Platform}" {@code this} {@link SampleService} is pulling from.
     */
    private static final Platform PLATFORM_INFO = new BasicPlatform("N/A", "Sample Service", "@");

    /**
     * Filepath to the sample content JSON.
     */
    private static final String SAMPLE_CONTENT_LOCATION = "src/main/resources/samplecontent/sample-content.json";

    /**
     * JSON mapper to instantiate {@link BasicContent} from the serialized data in {@link #SAMPLE_CONTENT_LOCATION}.
     */
    private static final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    /**
     * Constructs a {@link SampleService} instance with {@link #SOURCE_ID} {@code id}.
     *
     * @param id the unique identifier for the instantiated {@code SampleService}
     */
    public SampleService(ContentSourceId id) {
        super(id, PLATFORM_INFO);
    }

    /**
     * Checks if the {@link Platform} {@code this} {@link ContentSource} tries to pull from is available. Because this
     * is a sample {@code ContentSource}, it is always available.
     *
     * @return {@code true}, always
     */
    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public Content[] getLatestContent(int count) {
        int numToGet = Math.min(count, 6);
        BasicContent[] sampleContent = new BasicContent[0];
        try {
            sampleContent = mapper.readValue(
                    new File(SAMPLE_CONTENT_LOCATION),
                    BasicContent[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        applySourceId(sampleContent);

        return sampleContent;
    }

    @Override
    public Content[] getLatestContent(int count, Instant[] betweenTimes) {
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

        Content[] filteredContentArray = filteredContent.toArray(new Content[0]);
        applySourceId(filteredContentArray);

        return filteredContentArray;
    }

    /**
     * Applies {@code this} {@link SampleService} instance's {@link #SOURCE_ID} to the provided {@code content}.
     *
     * @param content the {@code Content} to apply {@code this} {@link SampleService} instance's {@link #SOURCE_ID} to
     */
    private void applySourceId(Content[] content) {
        for (Content piece : content) {
            piece.setOrigin(this.SOURCE_ID);
        }
    }
}
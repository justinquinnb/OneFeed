package com.justinquinnb.onefeed.contentsources.sample;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Producer;
import com.justinquinnb.onefeed.data.model.content.details.Platform;

import java.time.Instant;
import java.util.Arrays;

/**
 * A type of content that contains text and optional attachments,
 * posted by a user on some feed.
 */
public class SampleContent extends Content {
    public SampleContent(
            @JsonProperty("timestamp") Instant timestamp,
            @JsonProperty("contentUrl") String contentUrl,
            @JsonProperty("producer") Producer producer,
            @JsonProperty("platform") Platform source,
            @JsonProperty("text") String text,
            @JsonProperty("attachmentUrls") String[] attachmentUrls
    ) {
        super(timestamp, contentUrl, producer, source, text, attachmentUrls);
    }
}
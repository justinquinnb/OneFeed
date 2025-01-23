package com.justinquinnb.onefeed.data.sources.sample;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.content.details.Producer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * A type of content that contains text and optional attachments,
 * posted by a user on some feed.
 */
public class SampleContent extends Content {
    private String postUrl;
    private String caption;
    private String[] attachmentUrls;

    public SampleContent(
            @JsonProperty("timestamp") Instant timestamp,
            @JsonProperty("producer") Producer producer,
            @JsonProperty("platform") Platform source,
            @JsonProperty("postUrl") String postUrl,
            @JsonProperty("text") String caption,
            @JsonProperty("attachments") String[] attachmentUrls
    ) {
        super(timestamp, producer, source);
        this.postUrl = postUrl;
        this.caption = caption;
        this.attachmentUrls = attachmentUrls;
    }

    public String getPostUrl() {
        return this.postUrl;
    }

    public String getText() {
        return caption;
    }

    public String[] getAttachments() {
        return Arrays.copyOf(attachmentUrls, attachmentUrls.length);
    }
}
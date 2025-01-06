package com.justinquinnb.onefeed.data.model.content;

import com.justinquinnb.onefeed.data.model.content.details.Producer;
import com.justinquinnb.onefeed.data.model.content.details.Platform;

import java.time.LocalDateTime;

/**
 * User-generated content that exists within a feed.
 */
public abstract class Content {
    private LocalDateTime timestamp;
    private Producer producer;
    private Platform source;

    public Content(LocalDateTime timestamp, Producer producer, Platform source) {
        this.timestamp = timestamp;
        this.producer = producer;
        this.source = source;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public Producer getActor() {
        return this.producer;
    }

    public Platform getPlatform() {
        return this.source;
    }

    public abstract String getText();
}
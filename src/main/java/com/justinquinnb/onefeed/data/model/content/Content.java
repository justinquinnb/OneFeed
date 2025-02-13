package com.justinquinnb.onefeed.data.model.content;

import com.justinquinnb.onefeed.data.model.content.details.SourceInfo;
import com.justinquinnb.onefeed.data.model.content.details.Producer;

import java.time.Instant;

/**
 * User-generated content that exists within a feed.
 */
public abstract class Content {
    private final Instant timestamp;
    private final Producer producer;
    private final SourceInfo source;

    public Content(Instant timestamp, Producer producer, SourceInfo source) {
        this.timestamp = timestamp;
        this.producer = producer;
        this.source = source;
    }

    /**
     * Gets the timestamp at which {@code this} {@code Content} was published.
     *
     * @return the timestamp at which the content was published.
     */
    public Instant getTimestamp() {
        return this.timestamp;
    }

    /**
     * Gets the details of the person or entity that produced {@code this} {@code Content}.
     *
     * @return the details of the person or entity that produced {@code this} {@code Content}.
     */
    public Producer getProducer() {
        return this.producer;
    }

    /**
     * Gets the details of the platform where {@code this} {@code Content} was published.
     *
     * @return the details of the platform where {@code this} {@code Content} was published.
     */
    public SourceInfo getPlatform() {
        return this.source;
    }

    /**
     * Gets the text body of {@code this} {@code Content}.
     *
     * @return the text body of {@code this} {@code Content}.
     */
    public abstract String getText();

    /**
     * Gets the attachment URLs of {@code this} {@code Content}.
     *
     * @return the attachment URLs of {@code this} {@code Content}.
     */
    public abstract String[] getAttachments();

    public String toString() {
        return "Content:" + this.hashCode() +
                "{timestamp=" + this.timestamp +
                ", producer=" + this.producer +
                ", source=" + this.source + "}";
    }
}
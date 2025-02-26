package com.justinquinnb.onefeed.data.model.content;

import com.justinquinnb.onefeed.data.model.content.details.Producer;
import com.justinquinnb.onefeed.data.model.content.details.Platform;

import java.time.Instant;

/**
 * A single piece of content from some user-generated feed.
 */
public abstract class Content {
    /**
     * The {@link Instant} the content was posted to or generated on the {@link #platform}.
     */
    private final Instant timestamp;

    /**
     * The user or entity that produced {@code this} {@code Content}.
     */
    private final Producer producer;

    /**
     * The site or platform hosting {@code this} {@code Content}.
     */
    private final Platform platform;

    public Content(Instant timestamp, Producer producer, Platform platform) {
        this.timestamp = timestamp;
        this.producer = producer;
        this.platform = platform;
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
    public Platform getPlatform() {
        return this.platform;
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
                ", platform=" + this.platform + "}";
    }
}
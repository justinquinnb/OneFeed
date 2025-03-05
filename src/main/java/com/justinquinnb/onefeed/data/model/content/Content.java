package com.justinquinnb.onefeed.data.model.content;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justinquinnb.onefeed.data.model.content.details.ContentSourceId;
import com.justinquinnb.onefeed.data.model.source.ContentSource;

import java.time.Instant;

/**
 * A marker interface for a single piece of content from some user-generated feed.
 */
@JsonDeserialize(as = BasicContent.class)
public abstract class Content {
    /**
     * The {@link ContentSourceId} of the {@link ContentSource} instance that the {@code Content}
     * originates from.
     */
    private ContentSourceId origin;

    /**
     * The {@link Instant} the content was posted to or generated on the platform.
     */
    private Instant timestamp;

    /**
     * The URL of {@code this} content on its host platform.
     */
    private String contentUrl;

    public Content(ContentSourceId origin, Instant timestamp, String contentUrl) {
        this.origin = origin;
        this.timestamp = timestamp;
        this.contentUrl = contentUrl;
    }

    /**
     * Gets the {@code Content}'s {@link ContentSource} {@link #origin}.
     *
     * @return the {@link ContentSourceId} of the {@code ContentSource} {@code this} {@code Content} originated from
     */
    public ContentSourceId getOrigin() {
        return this.origin;
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
     * Gets the URL of {@code this} content on its host platform.
     *
     * @return URL of {@code this} content on its host platform
     */
    public String getContentUrl() {
        return this.contentUrl;
    }

    /**
     * Sets the origin of {@code this} {@code BasicContent}.
     * @param contentSourceId the {@link ContentSourceId} of the {@link ContentSource} {@code this} {@code BasicContent}
     *                        instance originates from.
     */
    public void setOrigin(ContentSourceId contentSourceId) {
        this.origin = contentSourceId;
    }
}